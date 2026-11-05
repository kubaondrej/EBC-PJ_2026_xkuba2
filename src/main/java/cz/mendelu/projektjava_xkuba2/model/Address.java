package cz.mendelu.projektjava_xkuba2.model;

public class Address {
    private String streetName;
    private String streetNumber;
    private String postcode;
    private String city;

    public Address(String streetName, String streetNumber, String postcode, String city) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postcode = postcode;
        this.city = city;
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

    @Override
    public String toString() {
        return streetName + " " + streetNumber + ", " + postcode + " " + city;
    }
}