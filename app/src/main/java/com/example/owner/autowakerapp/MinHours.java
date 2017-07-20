package com.example.owner.autowakerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by Owner on 7/9/2017.
 */

public class MinHours extends Activity{

    private double minHours = 5;
    private int maximumMinHours = 12;
    private int minimumMinHours = 5;

    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MinHours", "Opened the MinHours setting page.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.min_hours);
    }

    public void onButtonClick(View view) {
        if (view.getId() == R.id.homeButton){
            Intent i = new Intent(this, SleepSetup.class);
            finish();
        }
    }

    public double getMinHours() {
        return minHours;
    }

    public void setMinHours( double minHours ) {
        if (hoursInRange(minHours)){
            this.minHours = minHours;
        } else {
            // TODO: Send message back up.
        }

    }

    private boolean hoursInRange(double minHours){
        return ( ( minHours >= minimumMinHours ) && (minHours <= maximumMinHours) );
    }
}
