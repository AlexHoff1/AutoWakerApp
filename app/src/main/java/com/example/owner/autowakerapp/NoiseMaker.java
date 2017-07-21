package com.example.owner.autowakerapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * Created by Owner on 7/16/2017.
 */

public class NoiseMaker implements Runnable {

    private static boolean playing_;
    private static MediaPlayer music_;
    private SleepSetup sleep_setup_ = null;

    public NoiseMaker(SleepSetup sleep_setup) {
        this.sleep_setup_ = sleep_setup;
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        if (this.playing_) {
            try {
                this.music_.stop();
                this.playing_ = false;
            } catch (Exception e) {
                Log.i("NoiseMaker", "Object not yet created.");
            }
        } else {
            this.music_ = MediaPlayer.create(sleep_setup_, R.raw.daycore_pyromania);
            this.playing_ = true;
            this.music_.start();
        }
    }
}
