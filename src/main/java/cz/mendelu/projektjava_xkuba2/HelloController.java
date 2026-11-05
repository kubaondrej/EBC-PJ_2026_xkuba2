package cz.mendelu.projektjava_xkuba2;

import cz.mendelu.projektjava_xkuba2.model.Medication;
import cz.mendelu.projektjava_xkuba2.model.Pharmacy;
import cz.mendelu.projektjava_xkuba2.storage.JsonStorage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;

    private final JsonStorage storage = new JsonStorage();
    private final Path medicationsFile = Path.of("src", "main", "JSONs", "medications.json");
    private final Path pharmaciesFile = Path.of("data", "pharmacies.json");

    private List<Medication> medications = new ArrayList<>();
    private List<Pharmacy> pharmacies = new ArrayList<>();

    @FXML
    void CreatePharmacy() {
        pharmacies.add(new Pharmacy("Ondrova lékárna", "Oderská", "7", "625 00", "Brno"));
    }

    @FXML
    void AddMedication() {
    }

    @FXML
    void SaveData() {
        try {
            storage.saveList(medications, medicationsFile);

            //storage.saveList(pharmacies, pharmaciesFile);
        } catch (IOException e) {
            System.err.println("Chyba při ukládání: " + e.getMessage());
        }
        System.out.println("ULOZENO" + ' ' + medications.size() + ' ' + "polozek");

    }

    @FXML
    void LoadMeds() {
        try {
            medications = storage.loadList(medicationsFile, Medication.class);
        } catch (IOException e) {
            System.err.println("Chyba při načítání: " + e.getMessage());
        }
        System.out.println("NACTENO" + ' ' + medications.size() + ' ' + "polozek");


    }

    @FXML
    void AddMed() {

            medications.add(new Medication("M006", "Paralen", "Paracetamol", 500f));
            System.out.println("PRIDAN PARACETAMOL");
    }
    @FXML
    void PrintMeds() {
        for(Medication m:medications)
        {
            System.out.println(m.toString());
        }
    }
}