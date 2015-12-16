package com.example.darre.navdrawer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this, MainActivity.class));
        }

        else if (id == R.id.nav_overall) {
            startActivity(new Intent(this, OverallActivity.class));
        }

        else if (id == R.id.nav_findPetrol) {
            startActivity(new Intent(this, FindPetrol.class));
        }

        else if (id == R.id.nav_priceComp) {
            startActivity(new Intent(this, PriceComparison.class));
        }

        else if (id == R.id.nav_userPrefs) {
            startActivity(new Intent(this, UserPreferences.class));
        }

        else if (id == R.id.nav_about) {
            new AlertDialog.Builder(this)
                    .setTitle("About")
                    .setMessage("This application has been created to display the prices of petrol in the UK. The home page of the application displays the highest, lowest and average price of petrol across the entire UK.  There is also a search bar that allows the user to enter their location or a location of their choice to get a more defined result.\n" +
                            "There is also other features available to the user, this includes looking at google maps to get the location of nearby petrol stations, view a graph that compares the prices of each type of fuel over a certain time period, and also user preferences for the app.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
