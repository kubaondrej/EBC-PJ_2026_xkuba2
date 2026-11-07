package cz.mendelu.projektjava_xkuba2.service;
import cz.mendelu.projektjava_xkuba2.model.DispensingPoint;
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
    private static final Path dispensingPointsFile = Path.of("src", "main", "JSONs", "pharmacies.json");

    private static List<Medication> medications = new ArrayList<>();
    private static List<DispensingPoint> dispensingPoints = new ArrayList<>();


    private AppData() {}

    public static List<Medication> getMedications() { return medications; }
    public static List<DispensingPoint> getDispensingPoints() { return dispensingPoints; }

    public static Path getMedicationsFile() { return medicationsFile;}
    public static Path getDispensingPointsFile() { return dispensingPointsFile;}

    public static void load() {
        try {

            medications = storage.loadList(medicationsFile, Medication.class);
            dispensingPoints = storage.loadList(dispensingPointsFile, DispensingPoint.class);

            LoggingService.log("NAČTENO -> LÉKY" + ' ' + medications.size()+ ' ');
            LoggingService.log("NAČTENO -> VÝDEJ. MÍSTA" + ' ' + dispensingPoints.size()+ ' ');
        } catch (IOException e) {
            System.err.println("Chyba při načítání: " + e.getMessage());
        }
    }

    public static void save() {
        try {
            storage.saveList(medications, medicationsFile, Medication.class);
            storage.saveList(dispensingPoints, dispensingPointsFile, DispensingPoint.class);

            LoggingService.log("ULOŽENO <- LÉKY" + ' ' + medications.size()+ ' ');
            LoggingService.log("ULOŽENO <- VÝDEJ. MÍSTA" + ' ' + dispensingPoints.size()+ ' ');
        } catch (IOException e) {
            System.err.println("Chyba při ukládání: " + e.getMessage());
        }
    }
}