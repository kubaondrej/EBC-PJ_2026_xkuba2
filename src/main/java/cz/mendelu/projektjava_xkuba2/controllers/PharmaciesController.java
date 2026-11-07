package cz.mendelu.projektjava_xkuba2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class PharmaciesController {
    @FXML
    private void backToMenu(ActionEvent event)
    {
        SceneSwitcher.switchToMenu(event);
    }


}