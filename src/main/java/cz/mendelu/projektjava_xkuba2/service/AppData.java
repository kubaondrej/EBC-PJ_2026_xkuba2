package cz.mendelu.projektjava_xkuba2.service;
import cz.mendelu.projektjava_xkuba2.model.Pharmacy;
import cz.mendelu.projektjava_xkuba2.model.Medication;
import cz.mendelu.projektjava_xkuba2.storage.JsonStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public final class AppData {

    private static final JsonStorage storage = new JsonStorage();

    private static final Path medicationsFile = Path.of("src", "main", "JSONs", "medications.json");
    private static final Path pharmaciesFile = Path.of("src", "main", "JSONs", "pharmacies.json");

    private static List<Medication> medications = new ArrayList<>();
    private static List<Pharmacy> pharmacies = new ArrayList<>();


    private AppData() {}

    public static List<Medication> getMedications() { return medications; }
    public static List<Pharmacy> getPharmacies() { return pharmacies; }

    public static Path getMedicationsFile() { return medicationsFile;}
    public static Path getPharmaciesFile() { return pharmaciesFile;}

    public static void load() {
        try {

            medications = storage.loadList(medicationsFile, Medication.class);
            pharmacies = storage.loadList(pharmaciesFile, Pharmacy.class);

            LoggingService.log("NAČTENO -> LÉKY" + ' ' + medications.size()+ ' ');
            LoggingService.log("NAČTENO -> VÝDEJ. MÍSTA" + ' ' + pharmacies.size()+ ' ');
        } catch (IOException e) {
            System.err.println("Chyba při načítání: " + e.getMessage());
        }
    }

    public static void save() {
        try {
            storage.saveList(medications, medicationsFile, Medication.class);
            storage.saveList(pharmacies, pharmaciesFile, Pharmacy.class);

            LoggingService.log("ULOŽENO <- LÉKY" + ' ' + medications.size()+ ' ');
            LoggingService.log("ULOŽENO <- VÝDEJ. MÍSTA" + ' ' + pharmacies.size()+ ' ');
        } catch (IOException e) {
            System.err.println("Chyba při ukládání: " + e.getMessage());
        }
    }
}