package com.example.owner.autowakerapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * Created by Owner on 7/15/2017.
 */


public class WorkerThread implements Runnable {

    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        Log.i("Thread", "In thread.");
        Intent i = new Intent();

        BackgroundRunner testing = new BackgroundRunner();

        testing.onHandleIntent(i);
    }

}

