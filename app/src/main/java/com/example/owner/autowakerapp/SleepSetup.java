package com.example.owner.autowakerapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SleepSetup extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AsyncTask<URL, Integer, String> timeGetter;
    private MediaPlayer pyromania;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        try {
            timeGetter = new BackendLink("wakeTime");
            timeGetter.execute(new URL(retrieveFullURL()));
        } catch (Exception e) {
            Log.e("URL", "Some weird error happened.");
        }
        BackgroundRunner testing = new BackgroundRunner();
        this.pyromania = MediaPlayer.create(this, R.raw.daycore_pyromania);
    }

    public void onButtonClick(View view) {
        int id = view.getId();
        try {
            new Thread(new NoiseMaker(this)).start();
        } catch (Exception e) {
            Log.d("bug", "bug on click: " + e.toString());
        }
        Log.i("Interesting problem", "We made it here apparently.");
        if (id != 0) {
            Intent i = new Intent(this, SleepSetup.class);
            if (id == R.id.syncButton) {
                i = new Intent(this, BlutoothConnection.class);
            } else if (id == R.id.dateButton) {
                i = new Intent(this, DaysToWakeUp.class);
            } else if (id == R.id.homeButton) {
                try {
                    Log.i("Parse", "Trying to parse.");
                    i.setData(Uri.parse("http://192.168.56.1:8888/?date=2017/07/15&user=5T23R6"));
                    Log.i("Parse", "Parsed.");
                } catch (Exception e) {
                    Log.e("background", "Starting background failed." + e.toString());
                }
                Log.i("Threading", "Making a new thread.");
                new Thread(new WorkerThread()).start();
                return;
            } else if (id == R.id.timeButton) {
                i = new Intent(this, TimeForcer.class);
            } else if (id == R.id.minHoursButton) {
                i = new Intent(this, MinHours.class);
            }
            startActivityForResult(i, 0);
        } else {
            Log.d("Interesting", "ID was actually negative >.>");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sleep_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static final String WEBSITE_URL = "http://192.168.56.1:8888/";

    private String retrieveFullURL() {
        // TODO: Assemble appropriate link and parameters as a URL object, not string ;L
        String date = getCurrentDate();
        String current_user = getCurrentUser();
        //Path filePath = Paths.get(WEBSITE_URL, "sleep", current_user, date);
        //return filePath.toString();
        return WEBSITE_URL + "?date=" + date + "&user=" + current_user;
    }

    private String getCurrentDate() {
        // TODO: Clean this up
        DateFormat date_format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String date_string = date_format.format(date);
        return date_string;
    }

    private String getCurrentUser() {
        // TODO: Extract user from some config
        return "5T23R6";
    }

}
