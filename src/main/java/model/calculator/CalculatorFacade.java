package main.java.model.calculator;

import java.util.*;

// Author: Alexander Larnemo Ask, Jonatan Bunis, Vegard Landrö, Mohamad Melhem, Alexander Larsson Vahlberg
// Responsibility: The calculator's face outward towards the rest of the program.
// Used by: ModelAggregate.
// Uses: Exposes wanted functionality from the calculator outward.

public final class CalculatorFacade {

    private static final Calculator surplus = new ElectricitySurplusCalculator();
    private static final Calculator installationCost = new InstallationCostCalculator();
    private static final Calculator LCOE = new LevelizedCostOfElectricityCalculator();
    private static final Calculator panelProd = new SolarPanelProductionCalculator();
    private static final Calculator breakEven = new YearsToBreakEvenCalculator();



    private CalculatorFacade(){}

    //Checks which calculators have values from the input parameter as required values for running the calculation
    //Rechecks all calculators again after one calculation has run to see if the output from it matches the input to
    //another calculator
    public static HashMap<DataKey, Double> calculateAll(HashMap<DataKey, Double> wantedOutput) {

        HashMap<DataKey, Double> output = new HashMap<>(wantedOutput);

        List<Calculator> calculatorList = new ArrayList<>(Arrays.asList(surplus, installationCost, LCOE, panelProd, breakEven));

        Iterator<Calculator> calculatorIterator = calculatorList.iterator();

        while (calculatorIterator.hasNext()) {
            Calculator currentCalc = calculatorIterator.next();
            if (output.keySet().containsAll(currentCalc.getKeysOfRequiredInput())) {
                output = currentCalc.calculate(output);
                calculatorIterator.remove();                    //Remove used calculator from calculatorList
                calculatorIterator = calculatorList.iterator(); //Reset iterator to first in calculatorList
            }
        }
        return output;
    }

    //Checks all available calculators for specific output key
    //If a calculator is found it then checks if the input has the required values for running the calculation
    public static HashMap<DataKey, Double> calculateSpecificValue(DataKey key, HashMap<DataKey, Double> input) {

        List<Calculator> calculatorList = new ArrayList<>(Arrays.asList(surplus, installationCost, LCOE, panelProd, breakEven));

        for (Calculator currentCalc: calculatorList) {
            if (currentCalc.getKeysOfOutput().contains(key)) {
                if (input.keySet().containsAll((currentCalc.getKeysOfRequiredInput()))) {
                    return currentCalc.calculate(input);
                } else {
                    throw new NullPointerException("Insufficient data for meaningful answer");
                }
            }
        }
        throw new NullPointerException("No calculator is suited for calculating" + key);
    }
}
