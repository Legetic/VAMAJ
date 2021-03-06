package main.java.services.LocationCreator;

import main.java.model.property.Location;
import main.java.services.ApiJSONParser;
import main.java.services.ApiParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;


// Author: Alexander Larnemo Ask, Jonatan Bunis, Vegard Landrö, Mohamad Melhem, Alexander Larsson Vahlberg
// Responsibility: Implementation of ILocationCreator.
// Used by: (Lives in) ServiceFacade, PrimaryController.
// Uses: Creates location objects based on input from an API.

public class LocationCreatorAPI implements ILocationCreator {

    //Current year and month
    private int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    //Variables needed for dataCollection
    //Predefined latitude and longitude if they are not set.
    private double latitude = 63.825848;
    private double longitude = 20.263035;
    private int dataAccuracyInYears = 5;


    //Creates a location from an API using the LocationCreatorAPI's latitude and longitude
    @Override
    public Location createLocation() {

        Location l = new Location();
        l.setSolarInsolation(getSolarInsolation());
        l.setCoordinate(latitude, longitude);

        return l;
    }

    // Gathers the solar insolation from an API.
    private double getSolarInsolation() {
        //adjusting
        dataAccuracyInYears -= 1;

        //Uses a parser to get the correct objects and collect data through http.
        ApiParser<JSONObject> parser = new ApiJSONParser();


        //Try to read the data from the JsonObject and try to calculate average on it.
        //Getting the jsonObject from the API using the parser.
        JSONObject root = parser.readAPI("https://power.larc.nasa.gov/cgi-bin/v1/DataAccess.py?request=execute&identifier=SinglePoint&parameters=ALLSKY_SFC_SW_DWN&startDate=" + (currentYear - dataAccuracyInYears - 1) + "&endDate=" + (currentYear - 1) + "&lat=" + latitude + "&lon=" + longitude + "&userCommunity=SSE&tempAverage=INTERANNUAL&outputList=JSON&user=anonymous");

        if (!root.isEmpty()) {
            //Try to access JSONData
            JSONObject allSkyInsolationIncident = accessJsonData(root);

            if (!allSkyInsolationIncident.isEmpty()) {
                //Return average of data
                return getAverageInsolation(allSkyInsolationIncident, dataAccuracyInYears);
            }

        }
        //Default returnvalue
        return 0;
    }

    //Traverses the Json structure specific to the API (nasa) and returns the JSONobject that has the data directly.
    //This method belongs here and not in the ApiJSONParser since it is very specific to this API.
    private JSONObject accessJsonData(JSONObject root) {

        //Try to traverse as expected
        try {

            JSONArray features = (JSONArray) root.get("features");
            JSONObject properties = (JSONObject) features.get(0);
            properties = properties.getJSONObject("properties");
            JSONObject parameter = (JSONObject) properties.get("parameter");
            return (JSONObject) parameter.getJSONObject("ALLSKY_SFC_SW_DWN");

        } catch (Exception e) {
            //If object does'nt follow above structure, the api provides an error message.
            //Here we try to get the error message and throw it as a new error.
            //This should not need to be exception handled.

            JSONArray messages = (JSONArray) root.get("messages");
            JSONObject alert = (JSONObject) messages.get(0);
            alert = alert.getJSONObject("Alert");
            JSONObject description = (JSONObject) alert.get("Description");

            System.out.println(description);

            return new JSONObject();


        }


    }

    //returns the average kW-hr/m^2/day taking into account specified amount of years.
    //"years" must not exceed the amount of years from which data was gathered.
    private double getAverageInsolation(JSONObject dataSource, int yearsBack) {
        double average = 0;
        double sum = 0;
        int months = 0;
        //sum up data averages from the previous years.
        for (int i = 0; i <= yearsBack; i++) {
            for (String s : dataSource.keySet()) {
                //The datasource is unsorted so we need to match it.
                if (s.startsWith(Integer.toString(currentYear - i - 1))) {
                    months++;
                    sum += dataSource.getDouble(s);
                }
            }
        }

        if (months != 0) {
            //Calculate average
            average = sum / months;
        }


        return average;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
