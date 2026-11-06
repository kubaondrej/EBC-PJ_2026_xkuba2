package cz.mendelu.projektjava_xkuba2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class StatisticsController {

    @FXML
    private void backToMenu(ActionEvent event)
    {
        SceneSwitcher.switchToMenu(event);
    }


}