package com.example.owner.autowakerapp;

/**
 * Created by Owner on 7/9/2017.
 */

public class MinHours {

    private double minHours = 5;
    private int maximumMinHours = 12;
    private int minimumMinHours = 5;

    public double getMinHours() {
        return minHours;
    }

    public void setMinHours( double minHours ) {
        if (hoursInRange(minHours)){
            this.minHours = minHours;
        } else {
            // TODO: Send message back up.
        }

    }

    private boolean hoursInRange(double minHours){
        return ( ( minHours >= minimumMinHours ) && (minHours <= maximumMinHours) );
    }
}
