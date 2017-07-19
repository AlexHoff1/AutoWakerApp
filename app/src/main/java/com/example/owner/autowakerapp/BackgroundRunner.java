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

public class BackgroundRunner extends IntentService implements Runnable {
    public BackgroundRunner() {
        super("BackgroundRunner");
    }

    public BackgroundRunner(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    /*
     * Used to make this class runnable. Starts the service as a backend.
     */
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        this.onHandleIntent(new Intent());
    }


    public int onStartCommand() {
        this.onHandleIntent(new Intent());
        return 0;
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {

        BackendLink backendLink = new BackendLink("wakeTime");
        while (true) {
            tryGetResult(backendLink);
            // TODO: Use datetime objects and DT comparison similar to python.
            startQuerying(backendLink);
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
        pyromania.start();
    }

    private void startQuerying(BackendLink backendLink) {
        String currentTimeString = getCurrentTime();
        // TODO: Clean up the problem here. This currently just waits for result in a super inefficient way. :(.
        waitUntilResultExists(currentTimeString, backendLink);
        // TODO: Seriously patch this up. It is just checking if the time is less than currently.
        while (currentTimeString.compareTo(backendLink.getResult()) < 0 || currentTimeString.compareTo("12:00:00") > 0) {
            try {
                // Sleep for two minutes
                Log.i("Sleep", "Going to sleep for a bit. Current time is about: " + currentTimeString);
                Thread.sleep(120 * 1000);
                currentTimeString = getCurrentTime();
            } catch (Exception e) {
                Log.i("Sleep", "Sleep interupted.");
            }
        }
    }
}