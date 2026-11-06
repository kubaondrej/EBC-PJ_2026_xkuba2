package cz.mendelu.projektjava_xkuba2.service;
import cz.mendelu.projektjava_xkuba2.model.Medication;
import cz.mendelu.projektjava_xkuba2.model.Pharmacy;
import cz.mendelu.projektjava_xkuba2.storage.JsonStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public final class AppData {

    private static final JsonStorage storage = new JsonStorage();
    private static final Path medicationsFile = Path.of("src", "main", "JSONs", "medications.json");

    private static List<Medication> medications = new ArrayList<>();
    private static List<Pharmacy> pharmacies = new ArrayList<>();

    static {}

    private AppData() {}

    public static List<Medication> getMedications() { return medications; }
    public static List<Pharmacy> getPharmacies() { return pharmacies; }

    public static void load() {
        try {

            medications = storage.loadList(medicationsFile, Medication.class);
            LoggingService.log(medications.size()+ ' ' + "POLOŽEK LEKU"+"načteno");
        } catch (IOException e) {
            System.err.println("Chyba při načítání: " + e.getMessage());
        }
    }

    public static void save() {
        try {
            storage.saveList(medications, medicationsFile, Medication.class);
            LoggingService.log("ULOŽENO" + ' ' + medications.size()+ ' ' + "POLOŽEK LEKU");
        } catch (IOException e) {
            System.err.println("Chyba při ukládání: " + e.getMessage());
        }
    }
}