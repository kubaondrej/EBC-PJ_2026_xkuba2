package cz.mendelu.projektjava_xkuba2.model;

public class OverTheCounterMedication extends Medication {


    public OverTheCounterMedication() {}

    public OverTheCounterMedication(String name, String activeIngredient, float doseMgs) {
        super(name, activeIngredient,doseMgs);
    }


    @Override
    public boolean isPrescriptionRequired() { return false; }

    @Override
    public String getDispensingInfo() {
        return "Lék je volně prodejný";
    }
}