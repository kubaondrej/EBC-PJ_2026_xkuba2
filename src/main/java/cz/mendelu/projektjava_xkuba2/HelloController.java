package cz.mendelu.projektjava_xkuba2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import cz.mendelu.projektjava_xkuba2.storage.*;
import cz.mendelu.projektjava_xkuba2.model.*;

import java.io.IOException;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

//LEKY
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
    //tlacitka pro scenu s lekama
    @FXML
    private TextField medTxtId;
    @FXML
    private TextField medTxtName;
    @FXML
    private TextField medTxtActiveIngredient;
    @FXML
    private TextField medTxtDosage;
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
        loadData();
        loadDataIntoTable(tableMeds);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colActiveIngredient.setCellValueFactory(new PropertyValueFactory<>("activeIngredient"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("doseMgs"));
    }

    @FXML
    private void switchToMedDatabase(ActionEvent event) {
        switchScene(event, "medications_scene.fxml");
    }

    @FXML
    private void SwitchToPharmDatabase(ActionEvent event) {
        switchScene(event, "pharmacies_scene.fxml");
    }

    @FXML
    void loadData() {
        try {
            medications = storage.loadList(medicationsFile, Medication.class);
            pharmacies =storage.loadList(pharmaciesFile, Pharmacy.class);
        } catch (IOException e) {
            System.err.println("Chyba při načítání: " + e.getMessage());
        }
        System.out.println("NACTENO" + ' ' + medications.size() + ' ' + "polozek");
    }
    @FXML
    void saveData() {

    }

    @FXML
    private void loadDataIntoTable(TableView<Medication> targetTable) {
        if(targetTable==tableMeds)
        {
            ObservableList<Medication> observableList = FXCollections.observableArrayList(medications);
            tableMeds.setItems(observableList);
        }
    }

    @FXML
    private void addMedicationToDatabase()
    {
        try {
            medications.add(new Medication(medTxtId.getText(),medTxtName.getText(),medTxtActiveIngredient.getText(),Float.parseFloat(medTxtDosage.getText())));
            System.out.println("LÉK PŘIDÁN");
            loadDataIntoTable(tableMeds);
        }
        catch(NumberFormatException e)
        {
            e.printStackTrace();
            System.err.println("Chyba: Nepodařilo se přidat lék ");
        }

    }

    @FXML
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
}