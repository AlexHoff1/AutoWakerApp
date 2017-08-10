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
    private TextView time_;
    private TimePicker simple_time_picker_;
    protected void onCreate(Bundle saved_instance_state) {
        super.onCreate(saved_instance_state);
        setContentView(R.layout.time_forcer);
        time_ = (TextView) findViewById(R.id.time);
        simple_time_picker_ = (TimePicker)findViewById(R.id.timePicker); //initiate a time picker
        simple_time_picker_.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            //@Override
            public void onTimeChanged(TimePicker view, int hour_of_day, int minute) {
                Log.i("Time", hour_of_day + " " + minute);
                Toast.makeText(getApplicationContext(), hour_of_day + "  " + minute, Toast.LENGTH_SHORT).show();
                //time.setText("Time is :: " + hourOfDay + " : " + minute); // set the current time in text view
            }
        });
    }

    public void onButtonClick(View view) {
        if (view.getId() == R.id.homeButton) {
            Intent i = new Intent(this, SleepSetup.class);
            finish();
        }
    }


    public void onTimeChanged(TimePicker view, int hour_of_day, int minute) {
        String date = String.valueOf(hour_of_day).toString() + ":" + String.valueOf(minute).toString();
        Log.i("TimeForcer", "Time was changed to: " + date);
        BackendLink sender = new BackendLink("date");
        sender.send("sleepTime", date);
    }
}
