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
    private static boolean running_;
    private static boolean toggled_;
    private static int SLEEP_DURATION = 120*1000;  // Sleep duration in milliseconds.
    private final int CHECK_DURATION = 120;

    public BackgroundRunner() {
        super("BackgroundRunner");
    }

    public BackgroundRunner(String name) {
        super(name);
    }

    public BackgroundRunner(boolean toggled) {
        this();
        this.toggled_ = toggled;
    }

    /*
     * Used to make this class runnable. Starts checking the backend service for the time.
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
        // TODO: Threadsafe
        if (this.running_) {
            return;
        }
        BackendLink backend_link = new BackendLink("wakeTime");
        tryGetResult(backend_link);

        Log.i("Background", "Link established, trying to run.");
        while (this.toggled_) {
            this.running_ = true;
            try {
                // Continues to execute until the time is met.
                startQuerying(backend_link);
                Thread.sleep(SLEEP_DURATION);
            } catch (Exception e) {
                try {
                    Thread.sleep(SLEEP_DURATION);
                } catch (InterruptedException interrupt) {
                    Log.i("Sleep", "Sleep was interrupted.");
                }
                Log.e("Background", "Unknown exception occurred while querying the backend");
            }
        }
        Log.i("Background", "Finished running.");
        this.running_ = false;
    }
    private void tryGetResult(BackendLink a_link_to_server) {
        String result = null;
        while (result == null) {
            try {
                a_link_to_server.refreshResult();
                result = a_link_to_server.getResult();
                Thread.sleep(CHECK_DURATION);
            } catch (Exception e) {
                Log.i("Background", "Waiting on result.");
            }
        }
    }

    // Returns the current time.
    private String getCurrentTime() {
        return (new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime());
    }

    private void waitUntilResultExists(String current_time, BackendLink backend_link) {
        Log.i("Background", "Waiting until result is here.");
        while(true){
            try {
                int result = current_time.compareTo(backend_link.getResult());
                break;  // Result isn't null since that didn't toss an error.
            } catch (Exception e) {
                Log.i("Background", "Result doesn't exist yet, stalling the thread.");
                try {
                    // TODO: Find more useful stuff to do and optimize the ping time.
                    Thread.sleep(CHECK_DURATION);
                } catch (Exception nested_exception) {
                    Log.i("Background", "Sleep was interrupted " + nested_exception.toString());
                }
            }
        }
    }

    //TODO: Threading
    private void playMusic() {
        // TODO: Arbitrary choice of music?
        MediaPlayer music = MediaPlayer.create(this, R.raw.daycore_pyromania);
        music.start();
    }

    private int startQuerying(BackendLink backend_link) {
        // TODO: Use datetime objects and DT comparison similar to python.
        String current_time_string = getCurrentTime();
        Log.i("Background", "Current time is: " + current_time_string);
        // TODO: Clean up the problem here. This currently just waits for result in a super inefficient way. :(.
        waitUntilResultExists(current_time_string, backend_link);
        // TODO: Seriously patch this up. It is just checking if the time is less than currently.
        while (inRange(current_time_string, backend_link.getResult())) {
            Log.i("Background", "Time was in range.");
            try {
                Log.i("Background", "Stalling. Current time is about: " + current_time_string);
                Thread.sleep(120 * 1000);
                current_time_string = getCurrentTime();
                backend_link.refreshResult();
            } catch (Exception e) {
                Log.i("Background", "Sleep was interrupted " + e.toString());
            }
        }
        playMusic();
        Log.i("Background", "Time to wake up :D");
        return 0;
    }

    // Is the time less than the wake time or greater than noon (hard coded cycle start time).
    private boolean inRange(String current_time, String wake_time) {
        return current_time.compareTo(wake_time) < 0 && current_time.compareTo("12:00:00") > 0;
    }
}