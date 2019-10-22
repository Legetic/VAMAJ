package main.java.model.calculator;

public enum DataKey {
    SOLAR_PANEL_EFFICIENY,
    PRODUCED_ELECTRICITY,
    CONSUMED_ELECTRICITY,
    SURPLUS("Electricity Surplus"),
    INSTALLATION_COST ("Installation Cost"),
    GOVERNMENT_SUBVENTION,
    SUBVENTED_INSTALLATION_COST ("Subvented Installation Cost"),
    AVAILABLE_SPACE,
    REQUIRED_PANEL_SPACE,
    PANEL_PRICE,
    LEVELIZED_ELECTRICITY_COST,
    ANNUAL_ELECTRICITY_PRODUCTION,
    ANNUAL_OPERATION_COST,
    EXPECTED_LIFESPAN,
    SOLAR_PV_DAILY_ELECTRICITY("Solar PV Daily Electricity"),
    SOLAR_PANEL_AREA,
    SOLAR_PANEL_EFFICIENCY,
    AVERAGE_SOLAR_RADIATION,
    PANEL_PERFORMANCE_RATIO,
    IMPORTED_ELECTRICITY_PRICE_KWH,
    YEARS_TO_BREAK_EVEN ("Years to break even");

    private String description = "Value";

    private DataKey(String description) {
        this.description = description;
    }
    private DataKey() {
    }

    public String getDescription() {
        return description;
    }
}
