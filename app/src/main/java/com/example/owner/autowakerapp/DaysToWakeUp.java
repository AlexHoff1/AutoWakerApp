package com.example.owner.autowakerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by Owner on 7/9/2017.
 */

public class DaysToWakeUp extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("DaysToWakeUp", "Opened the DaysToWakeUp page.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.days_to_wake_up);
    }

    public void onButtonClick(View view) {
        if (view.getId() == R.id.homeButton){
            Intent i = new Intent(this, SleepSetup.class);
            finish();
        }
    }
}
