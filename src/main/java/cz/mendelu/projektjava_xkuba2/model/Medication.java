package cz.mendelu.projektjava_xkuba2.model;

import java.util.UUID;

//jakcson saskarny
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
//PRO JACKSON!! MUSI TADY BEJT!!!!!
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "prescriptionOnly")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PrescriptionMedication.class, name = "true"),
        @JsonSubTypes.Type(value = OverTheCounterMedication.class, name = "false")
})
public abstract class Medication {
    private String id = UUID.randomUUID().toString();
    String name;
    String activeIngredient;
    float doseMgs;

    public Medication(String name, String activeIngredient, float doseMgs) {
        this.name = name;
        this.activeIngredient = activeIngredient;
        this.doseMgs = doseMgs;
    }
    public Medication(){}

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
    @JsonIgnore
    public abstract String getDispensingInfo();
    @JsonIgnore
    public abstract boolean isPrescriptionRequired();
    @JsonIgnore
    public int getMaxPackages() { return 0; }
    @Override
    public String toString() { return "Lék s názvem" + ' ' +name + ','+' ' + "s aktivní látkou" + ' ' + activeIngredient + ' ' + "v obsahu" + ' ' +doseMgs + "mg." + ' '+ getDispensingInfo() + '.';}
}
