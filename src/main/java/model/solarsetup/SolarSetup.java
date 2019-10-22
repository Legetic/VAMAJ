package main.java.model.solarsetup;

import java.util.ArrayList;
import java.util.List;

//Object that holds the information about a solar setup.
public abstract class SolarSetup {

    // Member variables
    private List<SolarPanel> solarPanels;

    private double installationCost;
    private double availableSpace;
    private int orientation;
    private int angle;


    public SolarSetup() {
        this.solarPanels = new ArrayList<>();
        this.installationCost = 0;
        this.availableSpace = 0;
        this.orientation = 0;
        this.angle = 0;
    }

    //Getters
    public List<SolarPanel> getSolarPanels() {
        return this.solarPanels;
    }

    //Lazy instantiation, gets a solar panel
    public SolarPanel getASolarPanel() {
        if (solarPanels.isEmpty()) {
            setSolarPanelsStandard();
        }
        return solarPanels.get(0);

    }

    public int getAngle() {
        return angle;
    }

    //Sums up effeciency of solar panels.
    public double getTotalEProductionPerHour() {
        double totalEfficiency = 0.0;
        for(SolarPanel p : solarPanels){
            totalEfficiency += p.getProductionPerHour();
        }
        return totalEfficiency;
    }

    public double getInstallationCost() {
        return this.installationCost;
    }

    public double getAvailableSpace() {
        return availableSpace;
    }

    public int getOrientation() {
        return orientation;
    }

    public int getPanelAngle() {
        return angle;
    }

    // Returns the sum of each solar panel's annualoperationCost.
    public double getAnnualOperationCost() {
        double totalAnnualOperationCost = 0.0;
        for(SolarPanel p : solarPanels){
            totalAnnualOperationCost += p.getAnnualOperationCost();
        }
        return totalAnnualOperationCost;
    }
    //Returns the sum of each solar panel's productionperhour times hours in a year.
    public double getAnnualElectricityProduction() {
        double annualElectricityProduction = 0.0;
        for(SolarPanel p : solarPanels){
            annualElectricityProduction += p.getProductionPerHour();
        }
        annualElectricityProduction *= 8766;
        return annualElectricityProduction;
    }

    // Returns the sum of each solar panels area.
    public double getSolarPanelCoverage() {
        double solarPanelCoverage = 0.0;
        for(SolarPanel p : solarPanels){
            solarPanelCoverage += p.getSize();
        }
        return solarPanelCoverage;
    }

    //Setters
    public void setSolarPanels(List<SolarPanel> solarPanels) {
        this.solarPanels = solarPanels;
    }

    public void setInstallationCost(double installationCost) {
        this.installationCost = installationCost;
    }

    public void setAvailableSpace(double availableSpace) {
        this.availableSpace = availableSpace;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }


    // The solar panel typ is
    public void setSolarPanelsStandard() {
        SolarPanel templateSolarPanel = new StandardSolarPanel();
        setSolarPanels(templateSolarPanel);

    }

    public void setSolarPanelsPremium() {
        SolarPanel templateSolarPanel = new PremiumSolarPanel();
        setSolarPanels(templateSolarPanel);

    }

    //Fills the solarsetup solarpanel list with the amount of solar panels that fit in the available space.
    private void setSolarPanels(SolarPanel templateSolarPanel) {
        List<SolarPanel> solarPanels = new ArrayList<>();

        for (int i = 0; i < getNumberOfSolarPanels(templateSolarPanel); i++) {
            solarPanels.add(new StandardSolarPanel());
        }

        setSolarPanels(solarPanels);
    }

    // Gets the amount of solarPanels that fit in the available space.
    private int getNumberOfSolarPanels(SolarPanel templateSolarPanel) {

        int numberOfSolarPanels = 0;
        numberOfSolarPanels = (int) Math.floor(availableSpace / templateSolarPanel.getSize());

        return numberOfSolarPanels;
    }

}
