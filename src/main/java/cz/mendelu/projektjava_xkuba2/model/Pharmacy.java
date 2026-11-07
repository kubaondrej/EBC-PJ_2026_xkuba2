package cz.mendelu.projektjava_xkuba2.model;

import java.util.ArrayList;
import java.util.List;

public class Pharmacy extends DispensingPoint {

    public Pharmacy(String streetName, String streetNumber, String postcode, String city) {
        super(streetName,streetNumber,postcode,city);
    }

    public Pharmacy() {}


    @Override
    public String getDispensingPointType()
    {
        return "LÉKÁRNA";
    }
}

