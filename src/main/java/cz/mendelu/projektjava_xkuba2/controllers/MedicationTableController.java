package cz.mendelu.projektjava_xkuba2.controllers;

import cz.mendelu.projektjava_xkuba2.model.Medication;
import cz.mendelu.projektjava_xkuba2.service.AppData;
import cz.mendelu.projektjava_xkuba2.service.LoggingService;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicationTableController {

    @FXML private TableView<Medication> tableMeds;
    @FXML private TableColumn<Medication, String> colId;
    @FXML private TableColumn<Medication, String> colName;
    @FXML private TableColumn<Medication, String> colActiveIngredient;
    @FXML private TableColumn<Medication, String> colDosage;
    @FXML private TableColumn<Medication, String> colIsPrescriptionOnly;
    @FXML private TableColumn<Medication, String> colMaxPackages;

    //vyhledavani
    @FXML
    private TextField txtSearch;
    @FXML
    private Label lblMedInfo;
    @FXML
    private Label lblSearchAttribute;
    @FXML
    private CheckBox searchByActiveIngredientChbx;

    @FXML
    private Label entriesCountLabel;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colActiveIngredient.setCellValueFactory(new PropertyValueFactory<>("activeIngredient"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("doseMgs"));
        colIsPrescriptionOnly.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().isPrescriptionRequired() ? "Ano" : "Ne"));
        colMaxPackages.setCellValueFactory(cell -> {int max = cell.getValue().getMaxPackages();
        return new SimpleStringProperty(max > 0 ? String.valueOf(max) : "-");});
        loadDatabaseData();
    }

    @FXML
    public void loadDatabaseData()
    {
        TableHelper.loadDataIntoTable(tableMeds, AppData.getMedications());
        updateTableCountLabel();
    }
    @FXML
    public void loadDatabaseData(List<Medication> data)
    {
        TableHelper.loadDataIntoTable(tableMeds, data);
        updateTableCountLabel();
    }
    @FXML
    public Medication getSelectedEntry()
    {
        return tableMeds.getSelectionModel().getSelectedItem();
    }

    @FXML
    public ReadOnlyObjectProperty<Medication> selectedMedicationProperty()
    {
        return tableMeds.getSelectionModel().selectedItemProperty();
    }

    @FXML
    private void searchByActiveIngredientChbxAction()
    {

        //VYHLEDAVAME POMOCI AKTIVNI PRISADY
        if (searchByActiveIngredientChbx.isSelected())
        {
            colActiveIngredient.getStyleClass().setAll("highlightedColumn");
            colName.getStyleClass().setAll("");
            lblSearchAttribute.setText("Aktivní složka:");
            txtSearch.setPromptText("např. Alprazolam");
        }
        //VYHLEDAVAME POMOCI NAZVU
        else if (!searchByActiveIngredientChbx.isSelected())
        {
            colActiveIngredient.getStyleClass().setAll("");
            colName.getStyleClass().setAll("highlightedColumn");
            lblSearchAttribute.setText("Název:");
            txtSearch.setPromptText("např. Voltaren");

        }

        if(!txtSearch.getText().isEmpty()) {
            searchMedication();
        }
    }

    @FXML
    private void searchMedication()
    {
        if(!txtSearch.getText().isEmpty()) {
            List<Medication> searchResults = new ArrayList<>();
            LoggingService.log("HLEDÁM " + txtSearch.getText());
            String currentMedValue;
            for (Medication m : AppData.getMedications()) {
                if (searchByActiveIngredientChbx.isSelected()) {
                    currentMedValue = m.getActiveIngredient().substring(0, txtSearch.getText().length());
                }
                else
                {
                    currentMedValue = m.getName().substring(0, txtSearch.getText().length());
                }
                if (currentMedValue.equalsIgnoreCase(txtSearch.getText())) {
                    LoggingService.log("NALEZENO - " + m.getName());
                    searchResults.add(m);
                }
            }
            loadDatabaseData(searchResults);
        }
        else
        {
            loadDatabaseData();
        }

        updateTableCountLabel();
    }

    private void updateTableCountLabel()
    {
        if(TableHelper.checkIfEmpty(tableMeds))
        {
            entriesCountLabel.getStyleClass().setAll("error_label");
            entriesCountLabel.setText("NENALEZENA ŽÁDNÁ DATA");
        }
        else
        {
            entriesCountLabel.getStyleClass().setAll("okay_label");
            entriesCountLabel.setText("NALEZENO" + ' ' + Integer.toString(tableMeds.getItems().size()) + ' ' + "ZÁPISŮ");
        }
    }
    @FXML
    private void resetSearchFilter()
    {
        txtSearch.setText("");
        loadDatabaseData();
    }



}