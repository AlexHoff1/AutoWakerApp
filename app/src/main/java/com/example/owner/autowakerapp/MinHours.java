package com.example.owner.autowakerapp;

/**
 * Created by Owner on 7/9/2017.
 */

public class MinHours {

    private int minHours = 5;

    public int getMinHours() {
        return 5;
    }

    public void setMinHours( int minHours ) {
        if (minHours >= 12) {
            // TODO: Pass message back up
        } else if (minHours >= 5){
            this.minHours = minHours;
        }

    }
}
