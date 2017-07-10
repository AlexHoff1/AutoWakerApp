package com.example.owner.autowakerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by Owner on 7/9/2017.
 */

public class BlutoothConnection extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug", "Testing.");
        setContentView(R.layout.blutooth_connection);
    }

    public void onButtonClick(View view) {
        if (view.getId() == R.id.button5){
            Intent i = new Intent(this, SleepSetup.class);
            startActivity(i);
        }
    }
}
