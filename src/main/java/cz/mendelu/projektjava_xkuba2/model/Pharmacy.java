package cz.mendelu.projektjava_xkuba2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Pharmacy {
    private String name;
    private String streetName;
    private String streetNumber;
    private String postcode;
    private String city;
    private final List<StockItem> inventory=new ArrayList<>();


    public Pharmacy(String name, String streetName, String streetNumber, String postcode, String city) {
        this.name=name;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postcode = postcode;
        this.city = city;
    }
    public Pharmacy() {}

    public String getName() {
        return name;
    }

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

    public void setName(String name) {
        this.name = name;
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
    public void addToInventory(StockItem stockItem)
    {
        System.out.println("PŘIDÁVÁM" + ' ' + stockItem.getId() + ' ' + "V POČTU " + ' ' + stockItem.getAmount() + ' ' + "DO" + ' ' + this.toString());
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
        return "Lékárna" + ' ' + streetName + ' ' + streetNumber;
    }
}

