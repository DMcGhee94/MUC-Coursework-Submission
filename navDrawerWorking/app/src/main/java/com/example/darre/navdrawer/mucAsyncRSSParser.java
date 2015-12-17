package com.example.darre.navdrawer;

/**
 * Created by Darren McGhee
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

import java.util.ArrayList;

public class mucAsyncRSSParser extends AsyncTask<Void, Void, Void> {

    public ArrayList<FuelType> PAData; //Declares the array list
    public mucRSSParser PAParser; //Declares the "mucRSSParser" class

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        PAData = new ArrayList<FuelType>(); //sets up the array list
        PAParser = new mucRSSParser();
    }

    @Override
    protected Void doInBackground(Void... params) {
        while (true){
            try {
                PAData = PAParser.retrieveData(); //activates the "retrieveData" method from the "mucRSSParser" class
            }
            catch (Exception e) {
            }
            break; //finishes the "while" loop
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}
