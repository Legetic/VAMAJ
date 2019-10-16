package main.java.model.Property;

import main.java.model.Contract.Contract;
import main.java.model.SolarSetup.SolarSetup;

public class Property {

    // Member variables
    private SolarSetup solarSetup;
    private Location location;
    private Contract contract;
    private double consumption;

    // Constructor (empty)
    public Property() {
    }

    // Getters
    public Location getLocation() {
        return location;
    }

    public Contract getContract() {
        return contract;
    }

    public SolarSetup getSolarSetup() {
        return solarSetup;
    }

    public double getAvailableRoofArea() {
        return solarSetup.getAvailableRoofArea();
    }

    public int getOrientation() {
        return solarSetup.getOrientation();
    }

    public int getPanelAngle() {
        return solarSetup.getPanelAngle();
    }

    public double getLatitude() {
        return location.getCoordinate().getLatitude();
    }

    public double getConsumption() {
        return consumption;
    }

    // Setters
    public void setLocation(Location l) {
        this.location = l;
    }

    public void setContract(Contract c) {
        this.contract = c;
    }

    public void setSolarSetup(SolarSetup solarSetup) {
        this.solarSetup = solarSetup;
    }
}