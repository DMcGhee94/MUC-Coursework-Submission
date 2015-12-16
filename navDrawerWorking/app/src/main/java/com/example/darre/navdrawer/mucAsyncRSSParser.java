package com.example.darre.navdrawer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

import java.util.ArrayList;

public class mucAsyncRSSParser extends AsyncTask<Void, Void, Void> {

    public ArrayList<FuelType> PAData;
    public mucRSSParser PAParser;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        PAData = new ArrayList<FuelType>();
        PAParser = new mucRSSParser();
    }

    @Override
    protected Void doInBackground(Void... params) {
        while (true){
            try {
                PAData = PAParser.retrieveData();
            }
            catch (Exception e) {
            }
            break;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}
