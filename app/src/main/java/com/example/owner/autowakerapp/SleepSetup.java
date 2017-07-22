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

    private static final String WEBSITE_URL = "http://192.168.56.1:8888/";

    private AsyncTask<URL, Integer, String> time_getter_;
    private MediaPlayer music_;

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

        NavigationView navigation_view = (NavigationView) findViewById(R.id.nav_view);
        navigation_view.setNavigationItemSelectedListener(this);
        try {
            time_getter_ = new BackendLink("wakeTime");
            time_getter_.execute(new URL(retrieveFullURL()));
        } catch (Exception e) {
            Log.e("Sleep", "Error occurred while creating a backendlink: " + e.toString());
        }
        this.music_ = MediaPlayer.create(this, R.raw.daycore_pyromania);
    }

    public void onButtonClick(View view) {
        int id = view.getId();
        try {
            new Thread(new NoiseMaker(this)).start();
        } catch (Exception e) {
            Log.e("Main", "Creating thread for noise maker failed with: " + e.toString());
        }
        if (id != 0) {
            Intent intent = new Intent(this, SleepSetup.class);
            if (id == R.id.syncButton) {
                intent = new Intent(this, BlutoothConnection.class);
            } else if (id == R.id.dateButton) {
                intent = new Intent(this, DaysToWakeUp.class);
            } else if (id == R.id.timeButton) {
                intent = new Intent(this, TimeForcer.class);
            } else if (id == R.id.minHoursButton) {
                intent = new Intent(this, MinHours.class);
            } else {
                // By default set the home button and check whether or not they are syncing.
                return;
            }
            if (intent == null) {
                return;
            }
            startActivityForResult(intent, 100);
        } else {
            Log.d("Main", "ID was 0.");
        }
    }

    public void onToggle(View view) {
        Intent intent = new Intent(this, SleepSetup.class);
        intent.setData(Uri.parse(retrieveFullURL()));
        new Thread(new BackgroundRunner()).start();
        return;
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

    private String retrieveFullURL() {
        // TODO: Assemble appropriate link and parameters as a URL object, not string ;L
        String date = getCurrentDate();
        String current_user = getCurrentUser();
        //Path filePath = Paths.get(WEBSITE_URL, "sleep", current_user, date);
        //return filePath.toString();
        return WEBSITE_URL + "?date=" + date + "&user=" + current_user;
    }

    private String getCurrentDate() {
        DateFormat date_format = new SimpleDateFormat("yyyy/MM/dd");
        return date_format.format(new Date());
    }

    private String getCurrentUser() {
        // TODO: Extract user from some config
        return "5T23R6";
    }

}
