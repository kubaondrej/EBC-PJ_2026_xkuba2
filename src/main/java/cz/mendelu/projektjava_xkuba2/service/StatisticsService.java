package cz.mendelu.projektjava_xkuba2.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import cz.mendelu.projektjava_xkuba2.model.Medication;

public final class StatisticsService {
    private StatisticsService() {}

    public static int getMedicationCount()
    {
        return AppData.getMedications().size();
    }

    public static int getPrescriptionMedicationCount()
    {
        int prescriptionCount=0;

        for(Medication m:AppData.getMedications())
        {
            if(m.isPrescriptionRequired())
            {
                prescriptionCount++;
            }
        }
        return prescriptionCount;

    }
    public static int getOTCMedicationCount()
    {
        return getMedicationCount()-getPrescriptionMedicationCount();
    }

    public static long getFileSize(Path path)
    {
        long fileSizeInBytes=0;

        try
        {
             fileSizeInBytes = Files.size(path);
        }
        catch (IOException e)
        {
            e.printStackTrace();

            System.err.println("CHYBA PRI ZISKAVANI VELIKOSTI SOUBORU LEKAREN");
        }
        return fileSizeInBytes;
    }
}
