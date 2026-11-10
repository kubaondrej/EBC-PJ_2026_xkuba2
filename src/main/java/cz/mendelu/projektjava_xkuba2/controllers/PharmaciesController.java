package cz.mendelu.projektjava_xkuba2.controllers;

import cz.mendelu.projektjava_xkuba2.model.Medication;
import cz.mendelu.projektjava_xkuba2.model.Pharmacy;
import cz.mendelu.projektjava_xkuba2.model.StockItem;
import cz.mendelu.projektjava_xkuba2.service.AppData;
import cz.mendelu.projektjava_xkuba2.service.LoggingService;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class PharmaciesController {
    @FXML private PharmaciesTableController pharmTableController;
    @FXML private MedicationsTableController medTableController;



    @FXML private Spinner<Integer> invCountSpinner;
    @FXML
    public void initialize()
    {
        LoggingService.log("INICIALIZACE pharmacies_scene.fxml");
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        invCountSpinner.setValueFactory(valueFactory);
    }
    @FXML private void addMedicationToStock()
    {
        String medIdToAdd=medTableController.getMainTableSelectedEntry().getId();
        Pharmacy target=pharmTableController.getMainTableSelectedEntry();
        boolean alreadyExists=false;
        if(medIdToAdd.isEmpty() || target==null)
        {
            LoggingService.log("CHYBA, NELZE PRIDAT LEK, NECO TU CHYBÍ");
        }
        else
        {
            for(StockItem s:target.getInventory())
            {
                if(s.getId()==medIdToAdd)
                {
                    alreadyExists=true;
                    break;
                }
            }
            if(!alreadyExists)
            {
                pharmTableController.getMainTableSelectedEntry().addToInventory(new StockItem(medTableController.getMainTableSelectedEntry().getId(), invCountSpinner.getValue()));
            }
            else
            {
                LoggingService.log("TENTO LEK JIZ VE STOCKU JE");
                //TODO, VYSKAKOVACI OKENKO
            }

        }
        pharmTableController.fillInventoryTable();
        AppData.save();
    }

    @FXML
    private void backToMenu(ActionEvent event)
    {
        SceneSwitcher.switchToMenu(event);
    }






}