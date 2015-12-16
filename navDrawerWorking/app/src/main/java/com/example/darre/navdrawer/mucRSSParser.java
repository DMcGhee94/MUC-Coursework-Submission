package com.example.darre.navdrawer;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class mucRSSParser {

    public String RSSFeedURL = "http://www.petrolprices.com/feeds/averages.xml?";

    private String result;
    private ArrayList<FuelType> myData;

    public mucRSSParser() {
        myData = new ArrayList<FuelType>();
    }

    public ArrayList<FuelType> retrieveData() {
        try {
            result = RSSFeedString(RSSFeedURL);
            ParseTheData();
            return myData;
        } catch (Exception ae) {
            return myData;
        }
    }

    private static String RSSFeedString(String urlString)throws IOException {
        String result = "";
        InputStream anInStream = null;
        int response = -1;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        // Checks the connection to the stream
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try {
            // Opens the connection
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            // Checks that the connection is Ok
            if (response == HttpURLConnection.HTTP_OK) {
                // if connection is ok it opens the stream
                anInStream = httpConn.getInputStream();
                InputStreamReader in= new InputStreamReader(anInStream);
                BufferedReader bin= new BufferedReader(in);

                // Reads the data from the XML stream
                bin.readLine(); // Throws away the header
                String line = new String();
                while (( (line = bin.readLine())) != null) {
                    result = result + "\n" + line;
                }
            }
        } catch (Exception ex){
            throw new IOException("Error connecting");
        }

        // Returns the result as a string value
        return result;
    }

    private void ParseTheData() throws IOException {
        try {
            String fuelName = "", highest = "", average = "", lowest = "";

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(result));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equalsIgnoreCase("fuel")) {
                        fuelName = xpp.getAttributeValue(null, "type");
                        Log.i("XML", "Found the fuel type: "+ fuelName);
                    }
                    else
                    {
                        if (xpp.getName().equalsIgnoreCase("highest")) {
                            highest = xpp.nextText();
                            Log.i("XML", "Found the highest: " + highest);
                        }
                        else
                        {
                            if (xpp.getName().equalsIgnoreCase("average")) {
                                average = xpp.nextText();
                                Log.i("XML", "Found the average: " + average);
                            }
                            else
                            {
                                if (xpp.getName().equalsIgnoreCase("lowest")) {
                                    lowest = xpp.nextText();
                                    Log.i("XML", "Found the lowest: " + lowest);
                                }
                            }
                        }
                    }
                }
                if (eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().equalsIgnoreCase("fuel")) {
                        myData.add(new FuelType(fuelName, highest, average, lowest));
                    }
                }
                eventType = xpp.next();
            }
            System.out.println(myData.get(1).Name + " " + myData.get(1).HighestString + " " + myData.get(1).AverageString + " " + myData.get(1).LowestString);
        }
        catch (Exception ae)
        {
            throw new IOException("Error parsing");
        }
    }
}
