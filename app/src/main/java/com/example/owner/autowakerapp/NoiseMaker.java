package com.example.owner.autowakerapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * Created by Owner on 7/16/2017.
 */

public class NoiseMaker implements Runnable {

    private static MediaPlayer pyromania;
    private SleepSetup sleep = null;
    public NoiseMaker(SleepSetup a){
        this.sleep = a;
    }
    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        if (pyromania!=null) {
            try {
                this.pyromania.stop();
            } catch (Exception e) {
                Log.i("Noise", "Object not yet created.");
            }
        }
        this.pyromania = MediaPlayer.create(sleep, R.raw.daycore_pyromania);
        if ( this.pyromania.getCurrentPosition() == 0 ) {
            this.pyromania.start();
        }
    }
}
