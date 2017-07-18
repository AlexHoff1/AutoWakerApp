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
    private static boolean playing = false;
    public NoiseMaker(SleepSetup a){
        this.sleep = a;
    }
    @Override
    public void run() {
        //android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        if (this.playing) {
            try {
                this.pyromania.stop();
                this.playing = false;
            } catch (Exception e) {
                Log.i("Noise", "Object not yet created.");
            }
        } else {
            this.pyromania = MediaPlayer.create(sleep, R.raw.daycore_pyromania);
            this.playing = true;
            this.pyromania.start();
        }
    }
}
