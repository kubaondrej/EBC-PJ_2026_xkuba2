package cz.mendelu.projektjava_xkuba2;

import cz.mendelu.projektjava_xkuba2.model.Medication;
import cz.mendelu.projektjava_xkuba2.model.OverTheCounterMedication;
import cz.mendelu.projektjava_xkuba2.model.Pharmacy;
import cz.mendelu.projektjava_xkuba2.model.PrescriptionMedication;
import cz.mendelu.projektjava_xkuba2.storage.JsonStorage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MenuController {

    @FXML
    private void switchToMedDatabase(ActionEvent event) {
        switchScene(event, "medications_scene.fxml");
    }

    @FXML
    private void SwitchToPharmDatabase(ActionEvent event) {
        switchScene(event, "pharmacies_scene.fxml");
    }





    @FXML
    private void switchScene(ActionEvent event, String fxmlFileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 1240, 500);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Chyba: Nepodařilo se načíst soubor " + fxmlFileName);
        }
    }
}