package com.example.owner.autowakerapp;

import android.util.Log;

import java.io.*;
import java.net.*;
import java.nio.*;

/**
 * Created by Owner on 7/9/2017.
 */

public class BackendLink {

    private static final String WEBSITE_URL = "www.yahoo.com";
    public void send(){

    }
    public void getWakeTime(){
        try {
            String fullURL = retrieveFullURL();
            URL base = new URL(fullURL);
            URLConnection opened_link = base.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(opened_link.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                Log.d("Sleep", "Information was retrieved.");
                // TODO: Add this wake time to a config file somewhere.
            }
            in.close();

        } catch (MalformedURLException e) {
            Log.d("Sleep", "Not sure why, but the URL is invlaid. Better check that out!");
        } catch (IOException e) {
            Log.d("Sleep", "Not sure why, but there was an IOException.");
        } catch (Exception e) {
            Log.d("Sleep", "Unknown exception occured.");
        }
    }

    public String retrieveFullURL() {
        String date = "";
        String current_user = "";
        //Path filePath = Paths.get(WEBSITE_URL, "sleep", current_user, date);
        //return filePath.toString();
        return "";
    }
}
