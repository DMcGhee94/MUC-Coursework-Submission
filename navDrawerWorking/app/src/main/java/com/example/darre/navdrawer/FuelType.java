package com.example.darre.navdrawer;

/**
 * Created by Darren McGhee
 */

public class FuelType {

    public String Name;
    public String HighestString;
    public String AverageString;
    public String LowestString;

    public FuelType(String fuelName, String highest, String average, String lowest) {
        //Sets the strings declared above to be the data that has been parsed
        Name = fuelName;
        HighestString = highest;
        AverageString = average;
        LowestString = lowest;
    }
}
