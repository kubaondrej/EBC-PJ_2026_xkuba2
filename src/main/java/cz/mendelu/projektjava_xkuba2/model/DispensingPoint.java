package cz.mendelu.projektjava_xkuba2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public abstract class DispensingPoint {

    private String streetName;
    private String streetNumber;
    private String postcode;
    private String city;
    private final List<StockItem> inventory=new ArrayList<>();


    public DispensingPoint(String streetName, String streetNumber, String postcode, String city) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postcode = postcode;
        this.city = city;
    }
    public DispensingPoint() {}

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCity() {
        return city;
    }

    public List<StockItem> getInventory() {
        return inventory;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setCity(String city) {
        this.city = city;
    }
    @JsonIgnore
    public abstract String getDispensingPointType();

    public void addToInventory(StockItem stockItem)
    {
        inventory.add(stockItem);
    }
    List<StockItem> getWarehouse()
    {
        return inventory;
    }

    public void printStockContents()
    {
        for (StockItem m : inventory) {
            System.out.println(m);
        }
    }

    @Override
    public String toString()
    {
        return getDispensingPointType() + ' ' + streetName + ' ' + streetNumber;
    }
}

