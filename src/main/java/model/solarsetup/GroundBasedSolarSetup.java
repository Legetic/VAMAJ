package main.java.model.SolarSetup;

import java.util.List;

public class GroundBasedSolarSetup extends SolarSetup {
    public GroundBasedSolarSetup(List <SolarPanel> solarPanels, double efficiency, double cost, double spaceRequired) {
        super(solarPanels, efficiency, cost, spaceRequired);
    }

}