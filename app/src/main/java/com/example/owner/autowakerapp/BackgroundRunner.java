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

    public int onStartCommand() {
        this.onHandleIntent(new Intent());
        return 0;
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        String dataString = workIntent.getDataString();
        // Do work here, based on the contents of dataString
        BackendLink test = new BackendLink("wakeTime");
        while (true) {
            tryGetResult(test);
            // TODO: Use datetime objects and DT comparison similar to python.
            String currentTime = getCurrentTime();
            // TODO: Clean up the problem here. This currently just waits for result in a super inefficient way. :(.
            waitUntilResultExists(currentTime, test);
            // TODO: Seriously patch this up. It is just checking if the time is less than currently.
            while (currentTime.compareTo(test.getResult()) < 0 || currentTime.compareTo("12:00:00") > 0) {
                try {
                    // Sleep for two minutes
                    Log.i("Sleep", "Going to sleep for a bit. Current time is about: " + currentTime);
                    Thread.sleep(120 * 1000);
                    currentTime = getCurrentTime();
                } catch (Exception e) {
                    Log.i("Sleep", "Sleep interupted.");
                }
            }
            playMusic();
            // At this point make some noise goes off
        }
    }
    private void tryGetResult(BackendLink aLinkToServer){
        try {
            aLinkToServer.getResult();
        } catch (Exception e) {
            Log.e("Background", "Something wierd happened!");
        }
    }

    private String getCurrentTime(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(cal.getTime());
        return currentTime;
    }

    private void waitUntilResultExists(String currentTime, BackendLink aLink) {
        Log.i("result", "Waiting until result is here.");
        while(true){
            try {
                int result = currentTime.compareTo(aLink.getResult());
                break;  // Result isn't null since that didn't toss an erorr.
            } catch (Exception e) {
                Log.i("result", "Result doesn't exist yet, going to sleep.");
                try {
                    // TODO: Find more useful stuff to do and optimize the ping time.
                    Thread.sleep(120);
                } catch (Exception e2 ){
                    Log.i("Sleep", "Interupted: " + e2.toString());
                }
            }
        }
    }

    //TODO: Threading
    private void playMusic() {
        // TODO: Arbitrary choice of music?
        final MediaPlayer pyromania = MediaPlayer.create(this, R.raw.daycore_pyromania);
        // TODO: find a way to stop the song >.<....
        pyromania.start();
    }
}