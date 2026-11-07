package cz.mendelu.projektjava_xkuba2.model;

import java.util.List;

public class VendingMachine extends DispensingPoint {
    final int typeLimit = 10;
    final int stockLimit = 5;

    public VendingMachine(String streetName, String streetNumber, String postcode, String city) {
        super(streetName,streetNumber,postcode,city);
    }

    public VendingMachine() {}

    public int getTypeLimit() {
        return typeLimit;
    }

    public int getStockLimit() {
        return stockLimit;
    }

    @Override
    public String getDispensingPointType()
    {
        return "VÝDEJNÍ AUTOMAT";
    }
}

