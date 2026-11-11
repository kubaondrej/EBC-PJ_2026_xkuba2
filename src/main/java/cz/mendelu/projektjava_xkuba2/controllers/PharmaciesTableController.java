package cz.mendelu.projektjava_xkuba2.controllers;

import cz.mendelu.projektjava_xkuba2.model.Medication;
import cz.mendelu.projektjava_xkuba2.model.Pharmacy;
import cz.mendelu.projektjava_xkuba2.model.StockItem;
import cz.mendelu.projektjava_xkuba2.service.AppData;
import cz.mendelu.projektjava_xkuba2.service.LoggingService;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PharmaciesTableController extends TableController<Pharmacy> {

      //tabulka lékáren
      @FXML private TableView<Pharmacy> pharmaciesTable;
      @FXML private TableColumn<Pharmacy, String> colName;
      @FXML private TableColumn<Pharmacy, String> colStreetName;
      @FXML private TableColumn<Pharmacy, String> colStreetNumber;
      @FXML private TableColumn<Pharmacy, String> colPostcode;
      @FXML private TableColumn<Pharmacy, String> colCity;

      //tabulka skaldu lékárny
      @FXML private TableView<StockItem> inventoryTable;
      @FXML private TableColumn<StockItem, String> colStockId;
      @FXML private TableColumn<StockItem, String> colStockName;
      @FXML private TableColumn<StockItem, String> colStockDoseMgs;
      @FXML private TableColumn<StockItem, String> colStockCount;

      @FXML
      public Label inventoryTableEntriesCountLabel;

      //sprava položek skladu
      @FXML private Button deleteStockEntryButton;
      @FXML private Button editStockCountButton;
      @FXML private Spinner<Integer> invCountEditSpinner;

      //vyhledavani lekaren
      @FXML private CheckBox searchByAddressChbx;

      //uprava/pridani lekaren
      @FXML private TextField pharmNameTxt;
      @FXML private TextField pharmStreetTxt;
      @FXML private TextField pharmStreetNumberTxt;
      @FXML private TextField pharmPSCTxt;
      @FXML private TextField pharmCityTxt;


      @Override
      public void initialize() {
            initializePharmTable();
            initializeInventoryTable();

            updateStockButtons();


            this.selectedEntryProperty().addListener((obs, oldMed, newMed) -> pharmSelectionChanged());
            inventoryTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateStockButtons());

            updateInventoryCountLabel();
            loadDatabaseData();

      }

      private void updateStockButtons() {
            String selectedStockName;
            float selectedStockDose;

            if (inventoryTable.getSelectionModel().getSelectedItem() == null) {
                  deleteStockEntryButton.setDisable(true);
                  deleteStockEntryButton.setText("VYMAZAT POLOŽKU");
                  editStockCountButton.setDisable(true);
            } else {
                  Medication medicationById = findMedicationById(inventoryTable.getSelectionModel().getSelectedItem().getId());
                  assert medicationById != null;
                  selectedStockName = medicationById.getName();
                  selectedStockDose = medicationById.getDoseMgs();
                  deleteStockEntryButton.setDisable(false);
                  deleteStockEntryButton.setText("VYMAZAT POLOŽKU" + ' ' + selectedStockName + ' ' + selectedStockDose + "mg");
                  editStockCountButton.setDisable(false);
            }

      }

      public StockItem getInventoryTableSelectedEntry() {
            return inventoryTable.getSelectionModel().getSelectedItem();
      }

      private void initializePharmTable() {
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colStreetName.setCellValueFactory(new PropertyValueFactory<>("streetName"));
            colStreetNumber.setCellValueFactory(new PropertyValueFactory<>("streetNumber"));
            colPostcode.setCellValueFactory(new PropertyValueFactory<>("postcode"));
            colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
      }

      private void initializeInventoryTable() {
            colStockId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colStockName.setCellValueFactory(cellData -> {
                  Medication med = findMedicationById(cellData.getValue().getId());
                  return new SimpleStringProperty(med != null ? med.getName() : "nevim");
            });
            colStockDoseMgs.setCellValueFactory(cellData -> {
                  Medication med = findMedicationById(cellData.getValue().getId());
                  return new SimpleStringProperty(med != null ? String.valueOf(med.getDoseMgs()) : "-");
            });
            colStockCount.setCellValueFactory(new PropertyValueFactory<>("amount"));

            SpinnerValueFactory<Integer> valueFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
            invCountEditSpinner.setValueFactory(valueFactory);
      }

      private Medication findMedicationById(String id) {
            for (Medication m : AppData.getMedications()) {
                  if (m.getId().equals(id)) {
                        return m;
                  }
            }
            return null;
      }

      @FXML
      public void editStockCount() {
            StockItem target = inventoryTable.getSelectionModel().getSelectedItem();
            if (target != null) {
                  target.setAmmount(invCountEditSpinner.getValue());
                  LoggingService.log("UPRAVEN POČET LÉKU" + ' ' + target.getId() + ' ' + "NA" + ' ' + target.getAmount());
                  AppData.save();
            }
            fillInventoryTable();
      }

      @FXML
      public void removeStockItem() {
            if (inventoryTable.getSelectionModel().getSelectedItem() != null) {
                  getMainTableSelectedEntry().removeStockFromInventory(inventoryTable.getSelectionModel().getSelectedItem());
            }
            fillInventoryTable(getMainTableSelectedEntry().getInventory());
            AppData.save();
      }

      private void updateInventoryCountLabel() {
            //inventar
            if (TableHelper.checkIfEmpty(inventoryTable)) {
                  inventoryTableEntriesCountLabel.getStyleClass().setAll("error_label");
                  inventoryTableEntriesCountLabel.setText("NENALEZENA ŽÁDNÁ DATA");
            } else {
                  inventoryTableEntriesCountLabel.getStyleClass().setAll("okay_label");
                  inventoryTableEntriesCountLabel.setText("NALEZENO" + ' ' + Integer.toString(inventoryTable.getItems().size()) + ' ' + "ZÁPISŮ");
            }
      }
//      public void inventorySelectionChanged()
//      {
//            updateStockButtons();
//      }

      public void pharmSelectionChanged() {
            Pharmacy selectedPharmacy = getMainTableSelectedEntry();

            if (selectedPharmacy != null) {
                  LoggingService.log("NAČÍTÁM SKLAD PRO LÉKÁRNU - " + selectedPharmacy.getName());
                  fillInventoryTable(selectedPharmacy.getInventory());

                  deleteStockEntryButton.setDisable(false);
                  editStockCountButton.setDisable(false);
            }
            updateStockButtons();

      }

      public void searchPharmacy() {
            if (!txtSearch.getText().isEmpty()) {
                  List<Pharmacy> searchResults = new ArrayList<>();
                  LoggingService.log("HLEDÁM LÉKÁRNU - " + txtSearch.getText());
                  String currentPharmValue;
                  for (Pharmacy p : AppData.getPharmacies()) {
                        if (searchByAddressChbx.isSelected()) {
                              currentPharmValue = p.getStreetName().substring(0, txtSearch.getText().length());
                        } else {
                              currentPharmValue = p.getName().substring(0, txtSearch.getText().length());
                        }
                        if (currentPharmValue.equalsIgnoreCase(txtSearch.getText())) {
                              LoggingService.log("LEKARNA NALEZENA - " + p.getName());
                              searchResults.add(p);
                        }
                  }
                  fillPharmacyTable(searchResults);
            } else {
                  loadDatabaseData();
            }
      }

      @Override
      public void loadDatabaseData() {
            TableHelper.loadDataIntoTable(pharmaciesTable, AppData.getPharmacies());
            if (getMainTableSelectedEntry() != null) {
                  TableHelper.loadDataIntoTable(inventoryTable, getMainTableSelectedEntry().getInventory());
            }
            inventoryTable.refresh();
            pharmaciesTable.refresh();
            updateMainTableCountLabel();
      }

      public void fillInventoryTable() {
            TableHelper.loadDataIntoTable(inventoryTable, getMainTableSelectedEntry().getInventory());
            inventoryTable.refresh();
            updateInventoryCountLabel();

      }

      public void fillInventoryTable(List<StockItem> data) {
            TableHelper.loadDataIntoTable(inventoryTable, data);
            inventoryTable.refresh();
            updateInventoryCountLabel();


      }

      public void fillPharmacyTable(List<Pharmacy> data) {
            TableHelper.loadDataIntoTable(pharmaciesTable, data);
            updateMainTableCountLabel();
      }

      @FXML
      public void pharmSearchAttributeChbxChanged() {
            //VYHLEDAVAME POMOCI NAZVU ULICE
            if (searchByAddressChbx.isSelected()) {
                  colStreetName.getStyleClass().setAll("highlightedColumn");
                  colName.getStyleClass().setAll("");
                  txtSearch.setPromptText("např. Oderská");
            }
            //VYHLEDAVAME POMOCI NAZVU
            else if (!searchByAddressChbx.isSelected()) {
                  colStreetName.getStyleClass().setAll("");
                  colName.getStyleClass().setAll("highlightedColumn");
                  txtSearch.setPromptText("např. Adamova lékárna");

            }

            if (!txtSearch.getText().isEmpty()) {
                  searchPharmacy();
            }
      }

      @Override
      public void updateMainTableCountLabel() {
            //lekarny
            if (TableHelper.checkIfEmpty(pharmaciesTable)) {
                  entriesCountLabel.getStyleClass().setAll("error_label");
                  entriesCountLabel.setText("NENALEZENA ŽÁDNÁ DATA");
            } else {
                  entriesCountLabel.getStyleClass().setAll("okay_label");
                  entriesCountLabel.setText("NALEZENO" + ' ' + Integer.toString(pharmaciesTable.getItems().size()) + ' ' + "ZÁPISŮ");
            }

      }

      @Override
      public Pharmacy getMainTableSelectedEntry() {
            return pharmaciesTable.getSelectionModel().getSelectedItem();
      }

      //POTŘEBUJEME TO FURT? NEJSPIS NE ALE NECHAM TO RADSI TADY
      @Override
      public ReadOnlyObjectProperty<Pharmacy> selectedEntryProperty() {
            return pharmaciesTable.getSelectionModel().selectedItemProperty();
      }

      public double getTablesHeightDifference()
      {
            return pharmaciesTable.getHeight() - inventoryTable.getHeight();
      }

      @FXML private void deletePharmacy()
      {
            if(getMainTableSelectedEntry()!=null)
            {
                  AppData.getPharmacies().remove(getMainTableSelectedEntry());
                  AppData.save();
                  fillInventoryTable(Collections.emptyList());
                  loadDatabaseData();
            }
      }
      @FXML private void addPharmacy()
      {
            String name = pharmNameTxt.getText();
            String streetName = pharmStreetTxt.getText();
            String streetNumber = pharmStreetNumberTxt.getText();
            String PSC = pharmPSCTxt.getText();
            String city = pharmCityTxt.getText();
            if(!name.isEmpty())
            {
                  AppData.getPharmacies().add(new Pharmacy(name,streetName,streetNumber,PSC,city));
                  AppData.save();
            }
            loadDatabaseData();
            int itemCount = pharmaciesTable.getItems().size();
            if(itemCount>0)
            {
                  pharmaciesTable.scrollTo(itemCount - 1);
                  pharmaciesTable.getSelectionModel().select(itemCount - 1);
            }
      }

      @FXML private void editPharmacy()
      {
            Pharmacy target = getMainTableSelectedEntry();
            int index=pharmaciesTable.getSelectionModel().getSelectedIndex();

            String name = pharmNameTxt.getText();
            String streetName = pharmStreetTxt.getText();
            String streetNumber = pharmStreetNumberTxt.getText();
            String PSC = pharmPSCTxt.getText();
            String city = pharmCityTxt.getText();

            //fuj
            if(!name.isEmpty())
            {
                  target.setName(name);
            }
            if(!streetName.isEmpty())
            {
                  target.setStreetName(streetName);
            }
            if(!streetNumber.isEmpty())
            {
                  target.setStreetNumber(streetNumber);
            }
            if(!PSC.isEmpty())
            {
                  target.setPostcode(PSC);
            }
            if(!city.isEmpty())
            {
                  target.setCity(city);
            }
            loadDatabaseData();
            pharmaciesTable.scrollTo(index);
            pharmaciesTable.getSelectionModel().select(index);
            AppData.save();

      }



}