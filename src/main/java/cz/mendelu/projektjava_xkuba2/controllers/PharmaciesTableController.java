package cz.mendelu.projektjava_xkuba2.controllers;

import cz.mendelu.projektjava_xkuba2.model.Pharmacy;
import cz.mendelu.projektjava_xkuba2.service.AppData;
import cz.mendelu.projektjava_xkuba2.service.LoggingService;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class PharmaciesTableController extends TableController<Pharmacy> {

      @FXML private TableView<Pharmacy> pharmaciesTable;
      @FXML private TableColumn<Pharmacy, String> name;
      @FXML private TableColumn<Pharmacy, String> colStreetName;
      @FXML private TableColumn<Pharmacy, String> colStreetNumber;
      @FXML private TableColumn<Pharmacy, String> colPostcode;
      @FXML private TableColumn<Pharmacy, String> colCity;

      //vyhledavani lekaren
      @FXML private CheckBox searchByAddressChbx;
      @Override
      public void initialize()
      {
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            colStreetName.setCellValueFactory(new PropertyValueFactory<>("streetName"));
            colStreetNumber.setCellValueFactory(new PropertyValueFactory<>("streetNumber"));
            colPostcode.setCellValueFactory(new PropertyValueFactory<>("postcode"));
            colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
            loadDatabaseData();
      }
      public void searchPharmacy()
      {
            if(!txtSearch.getText().isEmpty()) {
                  List<Pharmacy> searchResults = new ArrayList<>();
                  LoggingService.log("HLEDÁM LÉKÁRNU -"+' '+ txtSearch.getText());
                  String currentPharmValue;
                  for (Pharmacy p : AppData.getPharmacies())
                  {

                        currentPharmValue = p.getStreetName().substring(0, txtSearch.getText().length());

                        if (currentPharmValue.equalsIgnoreCase(txtSearch.getText()))
                        {
                              LoggingService.log("NALEZENO - " + p.getStreetName());
//                              searchResults.add(m);
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
      @Override
      public  void loadDatabaseData()
      {
            TableHelper.loadDataIntoTable(pharmaciesTable, AppData.getPharmacies());
            updateTableCountLabel();
      }

      @Override
      public void loadDatabaseData(List<Pharmacy> data)
      {
            TableHelper.loadDataIntoTable(pharmaciesTable, data);
            updateTableCountLabel();
      }

      @FXML public void pharmSearchAttributeChbxChanged()
      {
            //TODO
      }

      @Override
      public void updateTableCountLabel() {
            if (TableHelper.checkIfEmpty(pharmaciesTable)) {
                  entriesCountLabel.getStyleClass().setAll("error_label");
                  entriesCountLabel.setText("NENALEZENA ŽÁDNÁ DATA");
            } else {
                  entriesCountLabel.getStyleClass().setAll("okay_label");
                  entriesCountLabel.setText("NALEZENO" + ' ' + Integer.toString(pharmaciesTable.getItems().size()) + ' ' + "ZÁPISŮ");
            }
      }

      @Override
      public Pharmacy getSelectedEntry()
      {
            return pharmaciesTable.getSelectionModel().getSelectedItem();
      }

      //POTŘEBUJEME TO FURT? NEJSPIS NE ALE NECHAM TO RADSI TADY
      @Override
      public ReadOnlyObjectProperty<Pharmacy> selectedEntryProperty()
      {
            return pharmaciesTable.getSelectionModel().selectedItemProperty();
      }
//
//    @FXML
//    private Label entriesCountLabel;
//
//    @FXML
//    public void initialize() {
//
//    }
//
//    @FXML
//    public void loadDatabaseData()
//    {
//        TableHelper.loadDataIntoTable(tableMeds, AppData.getMedications());
//        updateTableCountLabel();
//    }
//    @FXML
//    public void loadDatabaseData(List<Medication> data)
//    {
//        TableHelper.loadDataIntoTable(tableMeds, data);
//        updateTableCountLabel();
//    }
//    @FXML
//    public Medication getSelectedEntry()
//    {
//        return tableMeds.getSelectionModel().getSelectedItem();
//    }
//
//    @FXML
//    public ReadOnlyObjectProperty<Medication> selectedMedicationProperty()
//    {
//        return tableMeds.getSelectionModel().selectedItemProperty();
//    }
//
//    @FXML
//    private void searchByActiveIngredientChbxAction()
//    {
//
//        //VYHLEDAVAME POMOCI AKTIVNI PRISADY
//        if (searchByActiveIngredientChbx.isSelected())
//        {
//            colActiveIngredient.getStyleClass().setAll("highlightedColumn");
//            colName.getStyleClass().setAll("");
//            lblSearchAttribute.setText("Aktivní složka:");
//            txtSearch.setPromptText("např. Alprazolam");
//        }
//        //VYHLEDAVAME POMOCI NAZVU
//        else if (!searchByActiveIngredientChbx.isSelected())
//        {
//            colActiveIngredient.getStyleClass().setAll("");
//            colName.getStyleClass().setAll("highlightedColumn");
//            lblSearchAttribute.setText("Název:");
//            txtSearch.setPromptText("např. Voltaren");
//
//        }
//
//        if(!txtSearch.getText().isEmpty()) {
//            searchMedication();
//        }
//    }
//
//    @FXML
//    private void searchMedication()
//    {
//        if(!txtSearch.getText().isEmpty()) {
//            List<Medication> searchResults = new ArrayList<>();
//            LoggingService.log("HLEDÁM " + txtSearch.getText());
//            String currentMedValue;
//            for (Medication m : AppData.getMedications()) {
//                if (searchByActiveIngredientChbx.isSelected()) {
//                    currentMedValue = m.getActiveIngredient().substring(0, txtSearch.getText().length());
//                }
//                else
//                {
//                    currentMedValue = m.getName().substring(0, txtSearch.getText().length());
//                }
//                if (currentMedValue.equalsIgnoreCase(txtSearch.getText())) {
//                    LoggingService.log("NALEZENO - " + m.getName());
//                    searchResults.add(m);
//                }
//            }
//            loadDatabaseData(searchResults);
//        }
//        else
//        {
//            loadDatabaseData();
//        }
//
//        updateTableCountLabel();
//    }
//
//    private void updateTableCountLabel()
//    {
//        if(TableHelper.checkIfEmpty(tableMeds))
//        {
//            entriesCountLabel.getStyleClass().setAll("error_label");
//            entriesCountLabel.setText("NENALEZENA ŽÁDNÁ DATA");
//        }
//        else
//        {
//            entriesCountLabel.getStyleClass().setAll("okay_label");
//            entriesCountLabel.setText("NALEZENO" + ' ' + Integer.toString(tableMeds.getItems().size()) + ' ' + "ZÁPISŮ");
//        }
//    }
//    @FXML
//    private void resetSearchFilter()
//    {
//        txtSearch.setText("");
//        loadDatabaseData();
//    }
//


}