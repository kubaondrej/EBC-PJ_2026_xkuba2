package cz.mendelu.projektjava_xkuba2.controllers;
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

import java.util.ArrayList;
import java.util.List;

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
    //vyhledavani
    @FXML
    private TextField txtSearch;
    @FXML
    private Label lblMedInfo;

    //checkbox pro vlastnost na predpis
    @FXML
    private CheckBox isPrescriptionOnlyCheckbox;

    @FXML
    public void initialize() {
        LoggingService.log("INITIALIZACE pharmacies_scene.fxml");
        loadDataIntoTable(AppData.getMedications());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colActiveIngredient.setCellValueFactory(new PropertyValueFactory<>("activeIngredient"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("doseMgs"));
    }



    @FXML
    private void loadDataIntoTable(List<Medication> data) {
            LoggingService.log("NACITAM DATA DO TABULKY LEKU");

            ObservableList<Medication> observableList = FXCollections.observableArrayList(data);
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
            loadDataIntoTable(AppData.getMedications());
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
        loadDataIntoTable(AppData.getMedications());
        lblMedInfo.setText("Nevybrán žádný lék");
        txtSearch.setText("");
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
            medTxtPackagesPerPrescription.setText("");
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
    private void searchMedicationByName()
    {
        List<Medication> searchResults = new ArrayList<>();
        LoggingService.log("HLED8M - "+ txtSearch.getText());

        for(Medication m:AppData.getMedications())
        {
           if(txtSearch.getText().length()<=m.getName().length())
           {
                if(m.getName().substring(0,txtSearch.getText().length()).toLowerCase().equals(txtSearch.getText().toLowerCase()))
                {
                    LoggingService.log("NALEZENO - "+m.getName());
                    searchResults.add(m);
                }
           }
        }
        loadDataIntoTable(searchResults);
    }
    @FXML
    private void resetSearchFilter()
    {
        txtSearch.setText("");
        loadDataIntoTable(AppData.getMedications());
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