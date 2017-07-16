package com.example.owner.autowakerapp;

import android.content.Intent;

/**
 * Created by Owner on 7/15/2017.
 */


public class WorkerThread implements Runnable {

    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        Intent i = new Intent();

        BackgroundRunner testing = new BackgroundRunner();

        testing.onHandleIntent(i);
    }

}

