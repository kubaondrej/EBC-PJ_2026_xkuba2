package cz.mendelu.projektjava_xkuba2.controllers;
import cz.mendelu.projektjava_xkuba2.service.*;
import cz.mendelu.projektjava_xkuba2.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MedicationsController {

//LEKY
    @FXML
    private MedicationTableController medTableController;
    @FXML
    private Button backToMenuButton;


    //input boxy pro pridani leku
    @FXML
    private TextField medTxtName;
    @FXML
    private TextField medTxtActiveIngredient;
    @FXML
    private TextField medTxtDosage;
    @FXML
    private TextField medTxtPackagesPerPrescription;
    @FXML
    private Label lblMedInfo;

    //checkbox pro vlastnost na predpis
    @FXML
    private CheckBox isPrescriptionOnlyChbx;


    @FXML
    public void initialize() {
        LoggingService.log("INITIALIZACE pharmacies_scene.fxml");
        medTableController.selectedMedicationProperty().addListener((obs, oldMed, newMed) -> showMedicationInfo());

    }

    @FXML
    private void addMedicationToDatabase()
    {
        try {
            Medication medToAdd;

            if(isPrescriptionOnlyChbx.isSelected())
            {
                medToAdd = new PrescriptionMedication(medTxtName.getText(),medTxtActiveIngredient.getText(),Float.parseFloat(medTxtDosage.getText()), Integer.parseInt(medTxtPackagesPerPrescription.getText()));
            }
            else
            {
                medToAdd = new OverTheCounterMedication(medTxtName.getText(),medTxtActiveIngredient.getText(),Float.parseFloat(medTxtDosage.getText()));
            }
            AppData.getMedications().add(medToAdd);

            LoggingService.log("LÉK PŘIDÁN");
            medTableController.loadDatabaseData();
            AppData.save();
        }
        catch(NumberFormatException e)
        {
            e.printStackTrace();
            showErrorMessage("Nepodařilo se přidat lék","ŠPATNÝ FORMÁT");
            System.err.println("Chyba: Nepodařilo se přidat lék ");
        }
        medTxtName.setText("");
        medTxtActiveIngredient.setText("");
        medTxtDosage.setText("");
    }
    @FXML
    private void removeMedicationFromDatabase()
    {
        Medication selected = medTableController.getSelectedEntry();
        if (selected == null) {
            return;
        }
        AppData.getMedications().removeIf(m -> m.getId().equals(selected.getId()));
        AppData.save();
        medTableController.loadDatabaseData();
        lblMedInfo.setText("-");
    }

    @FXML
    private void prescriptionCheckboxAction()
    {
        LoggingService.log("CHECKBOX STATE:" + isPrescriptionOnlyChbx.isSelected());
        if(isPrescriptionOnlyChbx.isSelected())
        {
            medTxtPackagesPerPrescription.setDisable(false);
        }
        else
        {
            medTxtPackagesPerPrescription.setDisable(true);
            medTxtPackagesPerPrescription.setText("");
        }
    }

    @FXML
    private void showMedicationInfo()
    {
        Medication selected = medTableController.getSelectedEntry();
        if (selected == null) {
            lblMedInfo.setText("-");
            return;
        }
        LoggingService.log("KLIKNUTO NA POLOZKU V SEZNAMU S ID:"+selected.getId());
        lblMedInfo.setText(selected.toString());
    }

    @FXML
    private void backToMenu(ActionEvent event)
    {
        SceneSwitcher.switchToMenu(event);
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Chyba");
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }










}