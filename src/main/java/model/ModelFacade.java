package main.java.model;

import main.java.model.calculator.DataKey;
import main.java.model.contract.Contract;
import main.java.model.property.Location;
import main.java.model.solarsetup.SolarSetup;

public class ModelFacade {

    private ModelAggregate modelAggregate;

    // Constructor
    public ModelFacade() {
        modelAggregate = new ModelAggregate();
    }

    // Setters
    public void setLocation(Location location) {
        // TODO Implement
    }

    public void setContract(Contract contract) {
        modelAggregate.getProperty().setContract(contract);
    }

    public void setSolarSetup(SolarSetup solarSetup) {
        // TODO Implement
    }

    public void setContractCost(double cost) {
        setContract(getContract().setCost(5));
    }

    // Getters
    public Contract getContract() {
        return modelAggregate.getContract();
    }

    public SolarSetup getSolarSetup() {
        return modelAggregate.getSolarSetup();
    }

    public Location getLocation() {
        return modelAggregate.getLocation();
    }


    // Run calculations
    public void runCalculators() {
        modelAggregate.runCalculators();
    }

    // Getter(s)
    public Double getCalculationResults(DataKey key) {
        return modelAggregate.getCalculationResult(key);
    }
}
