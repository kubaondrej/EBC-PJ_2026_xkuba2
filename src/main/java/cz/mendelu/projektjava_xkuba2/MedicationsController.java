package cz.mendelu.projektjava_xkuba2;
import cz.mendelu.projektjava_xkuba2.storage.*;
import cz.mendelu.projektjava_xkuba2.model.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;



import java.io.IOException;

import java.nio.file.Path;

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

    //checkbox pro vlastnost na predpis
    @FXML
    private CheckBox isPrescriptionOnlyCheckbox;
//LEKARNY

    private final JsonStorage storage = new JsonStorage();
    //CESTY K JSONUM
    private final Path medicationsFile = Path.of("src", "main", "JSONs", "medications.json");
    private final Path pharmaciesFile = Path.of("src", "main", "JSONs", "pharmacies.json");
    //DATA LEKAREN A POBOCEK
    private List<Pharmacy> pharmacies = new ArrayList<>();
    private List<Medication> medications = new ArrayList<>();

    @FXML
    public void initialize() {
        System.out.println("INITIALIZACE");
        loadData();
        loadDataIntoTable(tableMeds);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colActiveIngredient.setCellValueFactory(new PropertyValueFactory<>("activeIngredient"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("doseMgs"));
    }


    @FXML
    void loadData() {
        try {
            medications = storage.loadList(medicationsFile, Medication.class);
            //pharmacies =storage.loadList(pharmaciesFile, Pharmacy.class);
        } catch (IOException e) {
            System.err.println("Chyba při načítání: " + e.getMessage());
        }
        System.out.println("NACTENO" + ' ' + medications.size() + ' ' + "polozek");
    }
    @FXML
    void saveData() {
        try {
            storage.saveList(medications, medicationsFile, Medication.class);
            //storage.saveList(pharmacies, pharmaciesFile);
        } catch (IOException e) {
            System.err.println("Chyba při ukládání: " + e.getMessage());
        }
        System.out.println("ULOZENO" + ' ' + medications.size() + ' ' + "polozek");
    }

    @FXML
    private void loadDataIntoTable(TableView<Medication> targetTable) {
        if(targetTable==tableMeds)
        {
            ObservableList<Medication> observableList = FXCollections.observableArrayList(medications);
            tableMeds.setItems(observableList);
            colIsPrescriptionOnly.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().isPrescriptionRequired() ? "Ano" : "Ne"));
        }
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
            medications.add(medToAdd);

            System.out.println("LÉK PŘIDÁN");
            loadDataIntoTable(tableMeds);
        }
        catch(NumberFormatException e)
        {
            e.printStackTrace();
            System.err.println("Chyba: Nepodařilo se přidat lék ");
        }
        medTxtName.setText("");
        medTxtActiveIngredient.setText("");
        medTxtDosage.setText("");
    }

    @FXML
    private void prescriptionCheckboxAction()
    {
        System.out.println("CHECKBOX STATE:" + isPrescriptionOnlyCheckbox.isSelected());
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
    private void backToMenu(ActionEvent event)
    {
        switchScene(event, "menu_scene.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlFileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 1240, 500);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Chyba: Nepodařilo se načíst soubor " + fxmlFileName);
        }
    }

    @FXML
    private void reloadData()
    {
        loadData();
        loadDataIntoTable(tableMeds);
    }
}