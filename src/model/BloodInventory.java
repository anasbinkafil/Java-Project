package model;

public class BloodInventory {
    private String bloodGroup;
    private String location;
    private String hospitalName;
    private double distanceKm;
    private int availableBags;

    public BloodInventory(String bloodGroup, String location, String hospitalName, double distanceKm, int availableBags) {
        this.bloodGroup = bloodGroup;
        this.location = location;
        this.hospitalName = hospitalName;
        this.distanceKm = distanceKm;
        this.availableBags = availableBags;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getLocation() {
        return location;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public int getAvailableBags() {
        return availableBags;
    }

    public void setAvailableBags(int availableBags) {
        this.availableBags = availableBags;
    }
}
