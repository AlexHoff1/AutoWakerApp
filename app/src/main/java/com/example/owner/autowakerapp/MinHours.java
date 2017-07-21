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

    private double min_hours_ = 5.0;
    private double maximum_min_hours_ = 12.0;
    private double minimum_min_hours_ = 5.0;

    protected void onCreate(Bundle saved_instance_state) {
        Log.i("MinHours", "Opened the MinHours setting page.");
        super.onCreate(saved_instance_state);
        setContentView(R.layout.min_hours);
    }

    public void onButtonClick(View view) {
        if (view.getId() == R.id.homeButton) {
            Intent i = new Intent(this, SleepSetup.class);
            finish();
        }
    }

    public double getMinHours() {
        return min_hours_;
    }

    public void setMinHours( double min_hours ) {
        if (hoursInRange(min_hours_)){
            this.min_hours_ = min_hours;
        } else {
            // TODO: Send message back up.
        }

    }

    private boolean hoursInRange(double min_hours){
        return ( ( min_hours >= minimum_min_hours_ ) && (min_hours <= maximum_min_hours_) );
    }
}
