package cz.mendelu.projektjava_xkuba2;

import cz.mendelu.projektjava_xkuba2.service.AppData;
import cz.mendelu.projektjava_xkuba2.service.StatisticsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class StatisticsController {

    @FXML
    private Label lblMedicationCount;
    @FXML
    private Label lblPrescriptionMedicationCount;
    @FXML
    private Label lblOTCMedicationCount;
    @FXML
    private Label lblMedicationsFileSize;
    @FXML
    public void initialize() {
        lblMedicationCount.setText(Integer.toString(StatisticsService.getMedicationCount()));
        lblPrescriptionMedicationCount.setText(Integer.toString(StatisticsService.getPrescriptionMedicationCount()));
        lblOTCMedicationCount.setText(Integer.toString(StatisticsService.getOTCMedicationCount()));
        lblMedicationsFileSize.setText(Long.toString(StatisticsService.getFileSize(AppData.getPharmaciesFile())) + 'b');
    }
    @FXML
    private void backToMenu(ActionEvent event)
    {
        SceneSwitcher.switchToMenu(event);
    }


}