package com.example.owner.autowakerapp;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Owner on 7/15/2017.
 */

public class BackgroundRunner extends IntentService {
    public BackgroundRunner(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    public BackgroundRunner() {
        super("BackgroundRunner");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        String dataString = workIntent.getDataString();
        // Do work here, based on the contents of dataString
        BackendLink test = new BackendLink("wakeTime");
        while (true) {
            try {
                test.getResult();
            } catch (Exception e) {
                Log.e("Background", "Something wierd happened!");
            }
            // TODO: Use datetime objects and DT comparison similar to python.
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String currentTime = sdf.format(cal.getTime());
            String result = test.getResult();
            // TODO: Clean up the problem here. This currently just waits for result in a super inefficient way. :(.
            while(true){
                try {
                    currentTime.compareTo(test.getResult());
                    break;
                } catch (Exception e) {
                    Log.i("Catching and sleeping.", "sleeeeep");
                    try {
                        // TODO: Find more useful stuff to do and optimize the ping time.
                        Thread.sleep(120);
                    } catch (Exception e2 ){
                        Log.i("Sleep", "Interupted: " + e2.toString());
                    }
                }
            }
            // TODO: Seriously patch this up. It is just checking if the time is less than currently.
            while (currentTime.compareTo(test.getResult()) < 0 || currentTime.compareTo("12:00:00") > 0) {
                try {
                    // Sleep for two minutes
                    Log.i("Sleep", "Going to sleep for a bit. Current time is about: " + currentTime);
                    Thread.sleep(120 * 1000);
                } catch (Exception e) {
                    Log.i("Sleep", "Sleep interupted.");
                }
            }
            // TODO: Arbitrary choice of music?
            final MediaPlayer pyromania = MediaPlayer.create(this, R.raw.daycore_pyromania);
            // TODO: find a way to stop the song >.<....
            pyromania.start();
            // At this point make some noise goes off
        }
    }
}