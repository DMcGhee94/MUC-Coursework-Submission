package com.example.darre.navdrawer;

/**
 * Created by Darren McGhee
 */

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class mucRSSParser {

    public String RSSFeedURL = "http://www.petrolprices.com/feeds/averages.xml?"; //Declares the URL to parse the data from

    private String result; //Declares the string for the results

    private ArrayList<FuelType> parsedData; //Declares array list to hold the data

    public mucRSSParser() {
        parsedData = new ArrayList<FuelType>();
    } //Sets up the array list to hold the data

    public ArrayList<FuelType> retrieveData() {
        try {
            result = RSSFeedString(RSSFeedURL); //Retrieves the data from the RSS feed
            ParseTheData(); //Activates the method below that picks out the data we need
            return parsedData;
        } catch (Exception ae) {
            return parsedData;
        }
    }

    private static String RSSFeedString(String urlString)throws IOException {
        String result = "";
        InputStream anInStream = null;
        int response = -1;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection)) // Checks the connection to the stream
            throw new IOException("Not an HTTP connection");
        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn; // Opens the connection
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) { // Checks that the connection is Ok
                anInStream = httpConn.getInputStream(); // if connection is ok it opens the stream
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
        return result; // Returns the result as a string value
    }

    //This method takes the data from the stream and picks out the data that I need from it
    private void ParseTheData() throws IOException {
        try {
            String fuelName = "", highest = "", average = "", lowest = ""; //Sets up the strings to hold the data from the stream

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(result)); //this looks for the first even type in the stream
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) { //Makes sure that the first tag is not the end of the document
                if (eventType == XmlPullParser.START_TAG) { //Looks for the start tag
                    if (xpp.getName().equalsIgnoreCase("fuel")) { //This looks for the first tag that is named "fuel"
                        fuelName = xpp.getAttributeValue(null, "type"); //sets the "fuelName" string as the type that is declared
                    }
                    else
                    {
                        if (xpp.getName().equalsIgnoreCase("highest")) { //This looks for the first tag that is named "highest"
                            highest = xpp.nextText(); //sets the "highest" string as the text after the tag
                        }
                        else
                        {
                            if (xpp.getName().equalsIgnoreCase("average")) { //This looks for the first tag that is named "average"
                                average = xpp.nextText(); //sets the "average" string as the text after the tag
                            }
                            else
                            {
                                if (xpp.getName().equalsIgnoreCase("lowest")) { //This looks for the first tag that is named "lowest"
                                    lowest = xpp.nextText(); //sets the "lowest" string as the text after the tag
                                }
                            }
                        }
                    }
                }
                if (eventType == XmlPullParser.END_TAG) { //When finished, it looks for the end of the document
                    if (xpp.getName().equalsIgnoreCase("fuel")) { //Each set of data is wrapped in a tag named "fuel"
                        parsedData.add(new FuelType(fuelName, highest, average, lowest)); //i created an object named "FuelType" and added it in to the array list
                    }
                }
                eventType = xpp.next(); //Moves on to the next event
            }
        }
        catch (Exception ae)
        {
            throw new IOException("Error parsing");
        }
    }
}
