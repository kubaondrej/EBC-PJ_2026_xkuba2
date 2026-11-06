package cz.mendelu.projektjava_xkuba2.model;

public class PrescriptionMedication extends Medication {
    private int maxPackagesPerPrescription;

    public PrescriptionMedication() {}

    public PrescriptionMedication(String name, String activeIngredient, float doseMgs, int maxPackagesPerPrescription) {
        super(name, activeIngredient,doseMgs);
        this.maxPackagesPerPrescription = maxPackagesPerPrescription;
    }

    public int getMaxPackagesPerPrescription() { return maxPackagesPerPrescription; }
    public void setMaxPackagesPerPrescription(int v) { this.maxPackagesPerPrescription = v; }

    @Override
    public boolean isPrescriptionRequired() { return true; }

    @Override
    public String getDispensingInfo() {
        return "Pouze na recept, max " + maxPackagesPerPrescription + " balni";
    }
}