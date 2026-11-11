package cz.mendelu.projektjava_xkuba2.controllers;

import cz.mendelu.projektjava_xkuba2.model.Medication;
import cz.mendelu.projektjava_xkuba2.model.StockItem;
import cz.mendelu.projektjava_xkuba2.service.AppData;
import cz.mendelu.projektjava_xkuba2.service.LoggingService;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import java.util.List;

public class MedicationsTableController extends TableController<Medication> {

    @FXML private TableView<Medication> medicationsTable;

    @FXML private TableColumn<Medication, String> colId;
    @FXML private TableColumn<Medication, String> colName;
    @FXML private TableColumn<Medication, String> colActiveIngredient;
    @FXML private TableColumn<Medication, String> colDosage;
    @FXML private TableColumn<Medication, String> colIsPrescriptionOnly;
    @FXML private TableColumn<Medication, String> colMaxPackages;

    //vyhledavani pro leky
    @FXML
    private CheckBox searchByActiveIngredientChbx;

    @Override
    public void initialize()
    {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colActiveIngredient.setCellValueFactory(new PropertyValueFactory<>("activeIngredient"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("doseMgs"));
        colIsPrescriptionOnly.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().isPrescriptionRequired() ? "Ano" : "Ne"));
        colMaxPackages.setCellValueFactory(cell -> {int max = cell.getValue().getMaxPackages();
        return new SimpleStringProperty(max > 0 ? String.valueOf(max) : "-");});
        loadDatabaseData();
    }

    public void fillMedicationsTable(List<Medication> data)
    {
        TableHelper.loadDataIntoTable(medicationsTable, data);
        updateMainTableCountLabel();
    }



    @Override
    public  void loadDatabaseData()
    {
        TableHelper.loadDataIntoTable(medicationsTable, AppData.getMedications());
        updateMainTableCountLabel();
    }

    @Override
    public Medication getMainTableSelectedEntry()
    {
        return medicationsTable.getSelectionModel().getSelectedItem();
    }




    @Override
    public ReadOnlyObjectProperty<Medication> selectedEntryProperty()
    {
        return medicationsTable.getSelectionModel().selectedItemProperty();
    }

    @FXML public void medSearchAttributeChbxChanged()
    {
        //VYHLEDAVAME POMOCI AKTIVNI PRISADY
        if (searchByActiveIngredientChbx.isSelected())
        {
            colActiveIngredient.getStyleClass().setAll("highlightedColumn");
            colName.getStyleClass().setAll("");
            txtSearch.setPromptText("např. Alprazolam");
        }
        //VYHLEDAVAME POMOCI NAZVU
        else if (!searchByActiveIngredientChbx.isSelected())
        {
            colActiveIngredient.getStyleClass().setAll("");
            colName.getStyleClass().setAll("highlightedColumn");
            txtSearch.setPromptText("např. Voltaren");
        }
        if(!txtSearch.getText().isEmpty()) {
            searchMedication();
        }
    }
    @Override
    public void updateMainTableCountLabel()
    {
        if(TableHelper.checkIfEmpty(medicationsTable))
        {
            entriesCountLabel.getStyleClass().setAll("error_label");
            entriesCountLabel.setText("NENALEZENA ŽÁDNÁ DATA");
        }
        else
        {
            entriesCountLabel.getStyleClass().setAll("okay_label");
            entriesCountLabel.setText("NALEZENO" + ' ' + Integer.toString(medicationsTable.getItems().size()) + ' ' + "ZÁPISŮ");
        }
    }
    public void searchMedication()
    {
        if(!txtSearch.getText().isEmpty()) {
            List<Medication> searchResults = new ArrayList<>();
            LoggingService.log("HLEDÁM LÉK" + txtSearch.getText());
            String currentMedValue; //AKTUALNI HODNOTA HLEDANEHO ATTRIBUTU AKTUALNIHO LEKU
            for (Medication m : AppData.getMedications()) {
                if (searchByActiveIngredientChbx.isSelected())
                {
                    currentMedValue = m.getActiveIngredient().substring(0, txtSearch.getText().length());
                }
                else
                {
                    currentMedValue = m.getName().substring(0, txtSearch.getText().length());
                }
                if (currentMedValue.equalsIgnoreCase(txtSearch.getText())) {
                    LoggingService.log("LÉK NALEZEN - " + m.getName());
                    searchResults.add(m);
                }
            }
            fillMedicationsTable(searchResults);
        }
        else
        {
            loadDatabaseData();
        }
    }


}
