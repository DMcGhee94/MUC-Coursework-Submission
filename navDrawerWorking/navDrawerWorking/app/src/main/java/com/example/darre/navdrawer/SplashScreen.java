package com.example.darre.navdrawer;

/**
 * Created by Darren McGhee
 */

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import java.util.ArrayList;

public class SplashScreen extends Activity {

    //Declares the timer that the splash screen is on
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class); //Sets up the new intent and defines what class to load next
                startActivity(i); //starts the next activity
                finish(); //finishes current activity
            }
        }, SPLASH_TIME_OUT);
    }
}