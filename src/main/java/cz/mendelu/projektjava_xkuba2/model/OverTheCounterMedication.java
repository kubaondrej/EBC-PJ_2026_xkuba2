package cz.mendelu.projektjava_xkuba2.model;

/**
 * Třída dědící z třídy Medication, je to její odnoš volně prodejná na předpis.
 */
public class OverTheCounterMedication extends Medication {

    public OverTheCounterMedication(String name, String activeIngredient, float doseMgs) {
        super(name, activeIngredient,doseMgs);
    }

    /**
     * Konstruktor pro Jackson
     */
    public OverTheCounterMedication() {}

    @Override
    public boolean isPrescriptionRequired() { return false; }

    @Override
    public String getDispensingInfo() {
        return "Lék je volně prodejný";
    }
}