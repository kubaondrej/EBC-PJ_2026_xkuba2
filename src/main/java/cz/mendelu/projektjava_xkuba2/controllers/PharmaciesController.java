package cz.mendelu.projektjava_xkuba2.controllers;

import cz.mendelu.projektjava_xkuba2.model.Medication;
import cz.mendelu.projektjava_xkuba2.model.Pharmacy;
import cz.mendelu.projektjava_xkuba2.model.StockItem;
import cz.mendelu.projektjava_xkuba2.model.VendingMachine;
import cz.mendelu.projektjava_xkuba2.service.AppData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class PharmaciesController {
    Pharmacy p1 = new Pharmacy("Oderská", "205/7", "625 00", "Brno");
    VendingMachine v1 = new VendingMachine("Karpatská", "103/20", "625 00", "Brno");


    @FXML
    private void backToMenu(ActionEvent event)
    {
        SceneSwitcher.switchToMenu(event);
    }

    @FXML
    private void testPharmacies()
    {
        System.out.println(p1.toString());
        System.out.println(v1.toString());
        AppData.getDispensingPoints().add(p1);
        AppData.getDispensingPoints().add(v1);
        AppData.save();
    }
    @FXML
    private void addMedicationToStock()
    {

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