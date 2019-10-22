package main.java.model.calculator;

import javax.xml.crypto.Data;
import java.util.*;

public final class CalculatorFacade {

    private static final Calculator surplus = new ElectricitySurplusCalculator();
    private static final Calculator installationCost = new InstallationCostCalculator();
    private static final Calculator LCOE = new LevelizedCostOfElectricityCalculator();
    private static final Calculator panelProd = new SolarPanelProductionCalculator();
    private static final Calculator breakEven = new YearsToBreakEvenCalculator();
    private static final List<Calculator> calculatorList = new ArrayList<>(Arrays.asList(
            surplus, installationCost, LCOE, panelProd, breakEven));


    private CalculatorFacade(){}

    public static HashMap<DataKey, Double> calculateAll(HashMap<DataKey, Double> wantedOutput){

        HashMap<DataKey, Double> output = new HashMap<>(wantedOutput);

        Iterator<Calculator> it = calculatorList.iterator();

        while (it.hasNext()){
            Calculator calc = it.next();
            if(output.keySet().containsAll(calc.getKeysOfRequiredInput())){
                System.out.println("Calculations successful for the: " + calc.toString());
                output = calc.calculate(output);
                it.remove();
                it = calculatorList.iterator();
            }else{
                System.out.println("Insufficient Input for the: " + calc.toString());

            }
        }
        return output;
    }


    public static HashMap<DataKey, Double> calculateSpecific(DataKey key, HashMap<DataKey, Double> input){
        for (Calculator calc: calculatorList) {
            if (calc.getKeysOfOutput().contains(key)) {
                if (input.keySet().containsAll((calc.getKeysOfRequiredInput()))) {
                    return calc.calculate(input);
                } else {
                    throw new NullPointerException("Insufficient data for meaningful answer");
                }
            }
        }
        throw new NullPointerException("No calculator is suited for calculating" + key);
    }
}
