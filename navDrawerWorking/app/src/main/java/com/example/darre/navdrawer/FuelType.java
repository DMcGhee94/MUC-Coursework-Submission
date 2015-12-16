package com.example.darre.navdrawer;

public class FuelType {

    public String Name;
    public String HighestString;
    public String AverageString;
    public String LowestString;

    public FuelType(String fuelName, String highest, String average, String lowest) {
        Name = fuelName;
        HighestString = highest;
        AverageString = average;
        LowestString = lowest;
    }
}
