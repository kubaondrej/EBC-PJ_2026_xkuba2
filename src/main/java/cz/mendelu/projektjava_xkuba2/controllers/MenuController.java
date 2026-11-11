package cz.mendelu.projektjava_xkuba2.controllers;

import cz.mendelu.projektjava_xkuba2.service.AppData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;

public class MenuController {

    @FXML private void switchToMedDatabase(ActionEvent event) {
        SceneSwitcher.switchTo(event, "medications_scene.fxml");
    }

    @FXML private void SwitchToPharmDatabase(ActionEvent event) {
        SceneSwitcher.switchTo(event, "pharmacies_scene.fxml");
    }

    @FXML private void SwitchToStatistics(ActionEvent event) {
        SceneSwitcher.switchTo(event, "statistics_scene.fxml", 450,300);
    }

    @FXML private void quit()
    {
        AppData.save();
        //JAKY JE ROZDIL MEZI PLATFORM/SYSTEM EXIT? TODO: OBJASNIT
        Platform.exit();
        System.exit(0);
    }






}