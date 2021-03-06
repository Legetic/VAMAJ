package main.java.model.calculator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static main.java.model.calculator.DataKey.*;

// Author: Alexander Larnemo Ask, Jonatan Bunis, Vegard Landrö, Mohamad Melhem, Alexander Larsson Vahlberg
// Responsibility: Calculator implementation.
// Used by: CalculatorFacade.
// Uses: Handles all calculations related to a property's installation cost of solar panels

final class InstallationCostCalculator implements Calculator {


    InstallationCostCalculator() {

    }


    // Returns the InstallationCostCalculator output consisting of installation cost, subvented amount and subvented installation cost
    @Override
    public HashMap<DataKey, Double> calculate(HashMap<DataKey, Double> input) {
        HashMap<DataKey, Double> data = new HashMap<>(input);

        double availableSpace = input.get(AVAILABLE_SPACE);
        double panelSize = input.get(PANEL_SIZE);
        double panelPrice = input.get(PANEL_PRICE);


        double installationCost = installationCost(availableSpace, panelSize, panelPrice);
        double governmentSubvention = subventionAmount(installationCost);
        double subventedInstallationCost = subventedCost(installationCost);

        data.put(INSTALLATION_COST, installationCost);
        data.put(GOVERNMENT_SUBVENTION, governmentSubvention);
        data.put(SUBVENTED_INSTALLATION_COST, subventedInstallationCost);

        return data;
    }

    // Calculates installationCost based on how many solar panels there's room for in the availableSpace of a property
    private double installationCost(double availableSpace, double panelSize, double panelPrice) {
        return Math.floor(availableSpace / panelSize) * panelPrice;
    }

    // Returns the subvented amount based on installationCost
    private double subventionAmount(double installationCost) {
        return installationCost * 0.2;
    }

    // Returns installationCost after subvention
    private double subventedCost(double installationCost) {
        return installationCost * 0.8;
    }

    @Override
    public Set<DataKey> getKeysOfRequiredInput() {
        return  new HashSet<>(Arrays.asList(AVAILABLE_SPACE, PANEL_SIZE, PANEL_PRICE));
    }

    @Override
    public Set<DataKey> getKeysOfOutput() {
        return new HashSet<>(Arrays.asList(INSTALLATION_COST, GOVERNMENT_SUBVENTION, SUBVENTED_INSTALLATION_COST));
    }

    @Override
    public String toString(){
        return "InstallationCostCalculator";
    }
}
