package com.example.owner.autowakerapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Owner on 7/9/2017.
 */

public class BlutoothConnection extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug", "Testing.");
        setContentView(R.layout.blutooth_connection);
    }
}
