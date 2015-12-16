package com.example.darre.navdrawer;

/**
 * Created by Darren McGhee
 */

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindPetrol extends FragmentActivity implements LocationListener {

    List<mucMapData> mapDataList; //declares the list used for map data

    public Marker[] mapDataMarkerList = new Marker[13]; //declares the marker array to a max of 13 markers
    public GoogleMap mapPetrolStations; //declares the google map and its name
    public LocationManager locationManager; //declares location manager and its name

    public LatLng currentLocation;

    private static final long MIN_TIME = 5000;
    private static final float MIN_DISTANCE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_petrol_activity_content);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mapPetrolStations = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap(); //access the map fragment in the layout

        try { //This allows the app to find the users current location
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        } catch (SecurityException se) {
        }

        mapDataList = new ArrayList<mucMapData>(); //sets up the array list for map data
        mucMapDataDBMgr mapDB = new mucMapDataDBMgr(this,"petrolAppDB.s3db",null,1); //accesses the "mucMapDataDBMgr" and the corresponding database
        try {
            mapDB.dbCreate(); //creates the map
        } catch (IOException e) {
            e.printStackTrace();
        }

        mapDataList = mapDB.allMapData();
        SetUpMap(); //Activates the method to set up the map
        AddMarkers(); //Activates the method to show the markers on the map
    }

    //sets the method to find the users location
    @Override
    public void onLocationChanged(Location location) {
        currentLocation = new LatLng(location.getLatitude(), location.getLongitude()); //Finds the current location of the user
        mapPetrolStations.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18)); //Moves the camera on the map to the current location
    }

    //Sets up the map
    public void SetUpMap() {
        if (mapPetrolStations != null) {
            mapPetrolStations.setMyLocationEnabled(true); //Enables current location button on map
            mapPetrolStations.getUiSettings().setCompassEnabled(true); //Shows the compass
            mapPetrolStations.getUiSettings().setMyLocationButtonEnabled(true); //Shows the button to move camera to current location
            mapPetrolStations.getUiSettings().setRotateGesturesEnabled(true); //Allows the user to rotate the camera
        }
    }

    //Adds the markers to the map
    public void AddMarkers() {
        MarkerOptions marker; //declares the marker options
        mucMapData mapData; //declares the map data
        String mrkTitle; //declares the string for the text to be displayed on the map when a marker is clicked
        String mrkText; //declares the string for the title that is displayed on the map when a marker is clicked

        for (int i = 0; i < mapDataList.size(); i++) { //when "i" is less than the size of "mapDataList", do the following
            mapData = mapDataList.get(i);
            mrkTitle = "Station Type: " + mapData.getStationType(); //Sets the title text displayed on the map
            mrkText = "Address: " + mapData.getStationAddress(); //sets the text displayed on the map
            marker = SetMarker(mrkTitle, mrkText, new LatLng(mapData.getLatitudeValue(), mapData.getLongitudeValue()), true); //Sets the marker with the title, text and its location using LatLang coordinates
            mapDataMarkerList[i] = mapPetrolStations.addMarker(marker); //adds the marker to the map
        }
    }

    public MarkerOptions SetMarker(String title, String snippet, LatLng position, boolean centreAnchor) {
        float anchorX;
        float anchorY;

        if(centreAnchor) {
            anchorX = 0.5f;
            anchorY = 0.5f;
        } else {
            anchorX = 0.5f;
            anchorY = 1f;
        }

        MarkerOptions marker = new MarkerOptions().title(title).snippet(snippet).icon(BitmapDescriptorFactory.defaultMarker()).anchor(anchorX, anchorY).position(position);
        return marker;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }
}
