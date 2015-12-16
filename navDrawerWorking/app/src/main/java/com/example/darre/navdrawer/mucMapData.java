package com.example.darre.navdrawer;

/**
 * Created by Darren McGhee
 */

import java.io.Serializable;

public class mucMapData implements Serializable {

    private int StationID;
    private String StationType;
    private String StationAddress;
    private float LatitudeValue;
    private float LongitudeValue;

    private static final long serialVersionUID = 0L;

    public int getStationID() {
        return StationID;
    }

    public void setStationID(int stationID) {
        this.StationID = stationID;
    }

    public String getStationType() {
        return StationType;
    }

    public void setStationType(String stationType) {
        this.StationType = stationType;
    }

    public String getStationAddress() {
        return StationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.StationAddress = stationAddress;
    }

    public float getLatitudeValue()
    {
        return LatitudeValue;
    }

    public void setLatitudeValue(float Lat)
    {
        this.LatitudeValue = Lat;
    }

    public float getLongitudeValue()
    {
        return LongitudeValue;
    }

    public void setLongitudeValue(float fLongitude)
    {
        this.LongitudeValue = fLongitude;
    }

    @Override
    public String toString() {
        String mapData;
        mapData = "mucPetrolStationInfo [StationID=" + StationID;
        mapData = ", Station Name=" + StationType;
        mapData = ", Station Address=" + StationAddress;
        mapData = ", Longitude=" + LongitudeValue;
        mapData = ", Latitude=" + LatitudeValue +"]";
        return mapData;
    }
}