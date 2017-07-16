package com.example.owner.autowakerapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TextView;


/**
 * Created by Owner on 7/9/2017.
 */

public class TimeForcer extends Activity {
    TextView time;
    TimePicker simpleTimePicker;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug", "Testing.");
        setContentView(R.layout.time_forcer);
        time = (TextView) findViewById(R.id.time);
        simpleTimePicker = (TimePicker)findViewById(R.id.timePicker); //initiate a time picker
        simpleTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            //@Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Log.d("Time", hourOfDay + " " + minute);
                Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
                //time.setText("Time is :: " + hourOfDay + " : " + minute); // set the current time in text view
            }
        });
    }

    public void onButtonClick(View view) {
        if (view.getId() == R.id.homeButton) {
            Intent i = new Intent(this, SleepSetup.class);
            finishActivity(0);
        }
    }


    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        // TODO Auto-generated method stub
        String date = String.valueOf(hourOfDay).toString() + ":" + String.valueOf(minute).toString();
    }
}
