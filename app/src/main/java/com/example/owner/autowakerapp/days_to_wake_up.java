package com.example.owner.autowakerapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Owner on 7/8/2017.
 */

public class days_to_wake_up extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug", "Testing.");
        setContentView(R.layout.days_to_wake_up);
    }
}
