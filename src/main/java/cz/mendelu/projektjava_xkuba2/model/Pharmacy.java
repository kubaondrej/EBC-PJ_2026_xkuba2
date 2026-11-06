package cz.mendelu.projektjava_xkuba2.model;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

public class Pharmacy {
    String name;
    Address address;
    private List<Medication> warehouse=new ArrayList<>(); //?


    public Pharmacy(String name, String streetName, String streetNumber, String postcode, String city) {
        this.name = name;
        this.address = new Address(streetName,streetNumber,postcode,city);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMedicationToWarehouse(Medication medication)
    {
        warehouse.add(medication);
    }
    List<Medication> getWarehouse()
    {
        return warehouse;
    }

    public void printWarehouseContents()
    {
        for (Medication m : warehouse) {
            System.out.println(m);
        }
    }


    @Override
    public String toString() { return "Lékarna:" + name + ' ' + "na ulici" + ' ' + address.getStreetName() + ' ' + address.getStreetNumber();
    }
}
