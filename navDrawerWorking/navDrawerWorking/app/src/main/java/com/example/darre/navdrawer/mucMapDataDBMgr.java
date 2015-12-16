package com.example.darre.navdrawer;

/**
 * Created by Darren McGhee
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class mucMapDataDBMgr extends SQLiteOpenHelper {

    private static final int DB_VER = 1;
    private static final String DB_PATH = "/data/data/com.example.darre.navdrawer/databases/";
    private static final String DB_NAME = "petrolAppDB.s3db";
    private static final String TBL_PETROLSTATIONS = "petrolStations";

    public static final String COL_STATIONID = "stationID";
    public static final String COL_STATIONTYPE = "stationType";
    public static final String COL_STATIONADDRESS = "stationAddress";
    public static final String COL_LATITUDEVALUE = "latitudeValue";
    public static final String COL_LONGITUDEVALUE = "longitudeValue";

    private final Context appContext;

    public mucMapDataDBMgr(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.appContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PETROLSTATIONS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TBL_PETROLSTATIONS + "("
                + COL_STATIONID + " INTEGER PRIMARY KEY," + COL_STATIONTYPE
                + " TEXT," + COL_STATIONADDRESS + " TEXT," + COL_LONGITUDEVALUE + " FLOAT,"
                + COL_LATITUDEVALUE + " FLOAT" +")";
        db.execSQL(CREATE_PETROLSTATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TBL_PETROLSTATIONS);
            onCreate(db);
        }
    }

    public void dbCreate() throws IOException {
        boolean dbExist = dbCheck();
        if(!dbExist){
            this.getReadableDatabase();
            try {
                copyDBFromAssets();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean dbCheck(){
        SQLiteDatabase db = null;
        try {
            String dbPath = DB_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
            db.setLocale(Locale.getDefault());
            db.setVersion(1);
        } catch(SQLiteException e) {
            Log.e("SQLHelper","Database not Found!");
        }
        if(db != null){
            db.close();
        }
        return db != null ? true : false;
    }

    private void copyDBFromAssets() throws IOException {
        InputStream dbInput = null;
        OutputStream dbOutput = null;
        String dbFileName = DB_PATH + DB_NAME;
        try {
            dbInput = appContext.getAssets().open(DB_NAME);
            dbOutput = new FileOutputStream(dbFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dbInput.read(buffer)) > 0) {
                dbOutput.write(buffer, 0, length);
            }
            dbOutput.flush();
            dbOutput.close();
            dbInput.close();
        } catch (IOException e) {
            throw new Error("Problems copying DB!");
        }
    }

    public void addaMapPetrolStationEntry(mucMapData aMapPetrolStation) {
        ContentValues values = new ContentValues();
        values.put(COL_STATIONID,aMapPetrolStation.getStationID());
        values.put(COL_STATIONTYPE, aMapPetrolStation.getStationType());
        values.put(COL_STATIONADDRESS, aMapPetrolStation.getStationAddress());
        values.put(COL_LONGITUDEVALUE, aMapPetrolStation.getLongitudeValue());
        values.put(COL_LATITUDEVALUE, aMapPetrolStation.getLatitudeValue());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TBL_PETROLSTATIONS, null, values);
        db.close();
    }

    public mucMapData getMapPetrolStationEntry(String aMapPetrolStationEntry) {
        String query = "Select * FROM " + TBL_PETROLSTATIONS + " WHERE " + COL_STATIONID + " =  \"" + aMapPetrolStationEntry + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        mucMapData MapDataEntry = new mucMapData();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            MapDataEntry.setStationID(Integer.parseInt(cursor.getString(0)));
            MapDataEntry.setStationType(cursor.getString(1));
            MapDataEntry.setStationAddress(cursor.getString(2));
            MapDataEntry.setLatitudeValue(Float.parseFloat(cursor.getString(3)));
            MapDataEntry.setLongitudeValue(Float.parseFloat(cursor.getString(4)));
            cursor.close();
        } else {
            MapDataEntry = null;
        }
        db.close();
        return MapDataEntry;
    }

    public List<mucMapData> allMapData() {
        String query = "Select * FROM " + TBL_PETROLSTATIONS;
        List<mucMapData> mucMapDataList = new ArrayList<mucMapData>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()==false) {
                mucMapData MapDataEntry = new mucMapData();
                MapDataEntry.setStationID(Integer.parseInt(cursor.getString(0)));
                MapDataEntry.setStationType(cursor.getString(1));
                MapDataEntry.setStationAddress(cursor.getString(2));
                MapDataEntry.setLatitudeValue(Float.parseFloat(cursor.getString(3)));
                MapDataEntry.setLongitudeValue(Float.parseFloat(cursor.getString(4)));
                mucMapDataList.add(MapDataEntry);
                cursor.moveToNext();
            }
        } else {
            mucMapDataList.add(null);
        }
        db.close();
        return mucMapDataList;
    }
}