package cz.mendelu.projektjava_xkuba2.model;

public class Medication {
    String id;
    String name;
    String activeIngredient;
    float doseMgs;

    public Medication(String id,String name, String activeIngredient, float doseMgs) {
        this.id=id;
        this.name = name;
        this.activeIngredient = activeIngredient;
        this.doseMgs = doseMgs;
    }
    public Medication() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActiveIngredient(String activeIngredient) {
        this.activeIngredient = activeIngredient;
    }

    public void setDoseMgs(float doseMgs) {
        this.doseMgs = doseMgs;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getActiveIngredient() {
        return activeIngredient;
    }

    public float getDoseMgs() {
        return doseMgs;
    }
    @Override
    public String toString() { return "Lék:" + ' ' + name + ' ' + "s aktivní látkou" + ' ' + activeIngredient + ' ' + "v obsahu" + ' ' +doseMgs + "mg";}
}
