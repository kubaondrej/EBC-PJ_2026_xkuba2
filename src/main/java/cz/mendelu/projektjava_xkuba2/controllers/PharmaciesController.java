package cz.mendelu.projektjava_xkuba2.controllers;

import cz.mendelu.projektjava_xkuba2.model.Medication;
import cz.mendelu.projektjava_xkuba2.model.Pharmacy;
import cz.mendelu.projektjava_xkuba2.model.StockItem;
import cz.mendelu.projektjava_xkuba2.service.AppData;
import cz.mendelu.projektjava_xkuba2.service.LoggingService;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class PharmaciesController {
    //Tabulka skladu lékárny
    @FXML private TableView inventoryTable;
    @FXML
    private PharmaciesTableController pharmTableController;
    @FXML private TableColumn<Medication, String> colId;
    @FXML private TableColumn<Medication, String> colName;
    @FXML private TableColumn<Medication, String> colDoseMgs;
    @FXML private TableColumn<StockItem, String> colCount;

    Pharmacy p1 = new Pharmacy("Apatyka Ondřej","Oderská", "205/7", "625 00", "Brno");
    Pharmacy p2 = new Pharmacy("Jonášovo lékovinové doupě","Jugoslávská", "768/9", "613 00", "Brno");

    @FXML
    public void initialize()
    {
        LoggingService.log("INICIALIZACE pharmacies_scene.fxml");

//        colId.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId()));
//        colName.setCellValueFactory(cell -> new SimpleStringProperty(
//                findMedication(cell.getValue().getId())
//                        .map(Medication::getName)
//                        .orElse("(neznamy lék)")));
//        colDoseMgs.setCellValueFactory(cell -> new SimpleStringProperty(
//                findMedication(cell.getValue().getId())
//                        .map(m -> String.valueOf(m.getDoseMgs()))
//                        .orElse("-")));
//        colCount.setCellValueFactory(cell -> new SimpleStringProperty(
//                String.valueOf(cell.getValue().getAmount())));
    }

    @FXML
    private void backToMenu(ActionEvent event)
    {
        SceneSwitcher.switchToMenu(event);
    }

    @FXML
    private void testPharmacies()
    {
        System.out.println(p1.toString());
        AppData.getPharmacies().add(p1);
        AppData.getPharmacies().add(p2);

        AppData.save();
    }
    @FXML
    private void addMedicationToStock()
    {
        p1.addToInventory(new StockItem("5e6f7a8b-9012-34ab-cdef-393939393939", 5));
        p1.addToInventory(new StockItem("5e6f7a8b-9012-34ab-cdef-333333333334", 7));
        AppData.save();
    }

    private Medication findMedicationById(String id)
    {
        for (Medication m:AppData.getMedications())
        {
            if(m.getId().equals(id))
            {
                return m;
            }
        }
        return null;
    }

}