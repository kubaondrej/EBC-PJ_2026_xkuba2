package cz.mendelu.projektjava_xkuba2;
import cz.mendelu.projektjava_xkuba2.service.*;
import cz.mendelu.projektjava_xkuba2.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MedicationsController {

//LEKY
    @FXML
    private Button backToMenuButton;
    //tabulka leku
    @FXML
    private TableView<Medication> tableMeds;
    @FXML
    private TableColumn<Medication, String> colId;
    @FXML
    private TableColumn<Medication, String> colName;
    @FXML
    private TableColumn<Medication, String> colActiveIngredient;
    @FXML
    private TableColumn<Medication, String> colDosage;
    @FXML
    private TableColumn<Medication, String> colIsPrescriptionOnly;
    //input boxy pro pridani leku
    @FXML
    private TextField medTxtName;
    @FXML
    private TextField medTxtActiveIngredient;
    @FXML
    private TextField medTxtDosage;
    @FXML
    private TextField medTxtPackagesPerPrescription;

    @FXML
    private Label lblMedInfo;

    //checkbox pro vlastnost na predpis
    @FXML
    private CheckBox isPrescriptionOnlyCheckbox;
//LEKARNY


    //DATA LEKAREN A POBOCEK
//    private List<Pharmacy> pharmacies = new ArrayList<>();
//    private List<Medication> medications = new ArrayList<>();

    @FXML
    public void initialize() {
        LoggingService.log("INITIALIZACE pharmacies_scene.fxml");
//        loadData();
        loadDataIntoTable();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colActiveIngredient.setCellValueFactory(new PropertyValueFactory<>("activeIngredient"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("doseMgs"));
    }



    @FXML
    private void loadDataIntoTable() {
            LoggingService.log("NACITAM DATA DO TABULKY LEKU");

            ObservableList<Medication> observableList = FXCollections.observableArrayList(AppData.getMedications());
            tableMeds.setItems(observableList);
            colIsPrescriptionOnly.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().isPrescriptionRequired() ? "Ano" : "Ne"));

    }

    @FXML
    private void addMedicationToDatabase()
    {
        try {
            Medication medToAdd;

            if(isPrescriptionOnlyCheckbox.isSelected())
            {
                medToAdd = new PrescriptionMedication(medTxtName.getText(),medTxtActiveIngredient.getText(),Float.parseFloat(medTxtDosage.getText()), Integer.parseInt(medTxtPackagesPerPrescription.getText()));
            }
            else
            {
                medToAdd = new OverTheCounterMedication(medTxtName.getText(),medTxtActiveIngredient.getText(),Float.parseFloat(medTxtDosage.getText()));
            }
            AppData.getMedications().add(medToAdd);

            LoggingService.log("LÉK PŘIDÁN");
            AppData.save();
            loadDataIntoTable();
        }
        catch(NumberFormatException e)
        {
            e.printStackTrace();
            showErrorMessage("Nepodařilo se přidat lék","ŠPATNÝ FORMÁT");
            System.err.println("Chyba: Nepodařilo se přidat lék ");
        }
        medTxtName.setText("");
        medTxtActiveIngredient.setText("");
        medTxtDosage.setText("");
    }
    @FXML
    private void removeMedicationFromDatabase()
    {
        Medication selected = tableMeds.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        AppData.getMedications().removeIf(m -> m.getId().equals(selected.getId()));
        AppData.save();
        loadDataIntoTable();
        lblMedInfo.setText("Nevybrán žádný lék");
    }

    @FXML
    private void prescriptionCheckboxAction()
    {
        LoggingService.log("CHECKBOX STATE:" + isPrescriptionOnlyCheckbox.isSelected());
        if(isPrescriptionOnlyCheckbox.isSelected())
        {
            medTxtPackagesPerPrescription.setDisable(false);
        }
        else
        {
            medTxtPackagesPerPrescription.setDisable(true);
        }
    }

    @FXML
    private void showMedicationInfo()
    {
        Medication selected = tableMeds.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        LoggingService.log("KLIKNUTO NA POLOZKU V SEZNAMU S ID:"+selected.getId());
        lblMedInfo.setText(selected.toString());

    }

    @FXML
    private void backToMenu(ActionEvent event)
    {
        SceneSwitcher.switchToMenu(event);
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Chyba");
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }




}