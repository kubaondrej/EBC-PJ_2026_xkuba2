package cz.mendelu.projektjava_xkuba2;

import cz.mendelu.projektjava_xkuba2.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;
    private Pharmacy p0;
    @FXML
    void CreatePharmacy() {
        p0 = new Pharmacy("Ondrova lékarna", "Oderská", "7", "625 00", "Brno");        System.out.println(p0.toString());
    }
    @FXML
    void AddMedication() {
        if (p0 == null) {
            p0 = new Pharmacy("Ondrova lékarna", "Oderská", "7", "625 00", "Brno");        System.out.println(p0.toString());
            return;
        }
        p0.addMedicationToWarehouse(new Medication("Valium", "Diazepam", 2.4f));
        p0.addMedicationToWarehouse(new Medication("MagneB6", "Magnesium", 50f));
        p0.printWarehouseContents();
    }
}
