package com.example.owner.autowakerapp;

import android.util.Log;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Owner on 7/9/2017.
 */

public class BackendLink {

    private static final String WEBSITE_URL = "https://sites.google.com/a/umn.edu/autowaker/";
    public void send() {

    }
    public void getWakeTime() {
        try {
            String full_url = retrieveFullURL();
            URL base = new URL(full_url);
            URLConnection opened_link = base.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(opened_link.getInputStream()));
            String input_line;
            while ((input_line = in.readLine()) != null){
                Log.d("Sleep", "Information was retrieved.");
                // TODO: Add this wake time to a config file somewhere.
                System.out.println(in.readLine());
            }
            in.close();

        } catch (MalformedURLException e) {
            Log.e("Sleep", "Not sure why, but the URL is invlaid. Better check that out!");
        } catch (IOException e) {
            Log.e("Sleep", "Not sure why, but there was an IOException.");
        } catch (Exception e) {
            Log.e("Sleep", "Unknown exception occured.");
        }
    }

    private String retrieveFullURL() {
        String date = getCurrentDate();
        String current_user = getCurrentUser();
        //Path filePath = Paths.get(WEBSITE_URL, "sleep", current_user, date);
        //return filePath.toString();
        return "";
    }

    private String getCurrentDate() {
        DateFormat date_format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String date_string = date_format.format(date);
        return date_string;
    }

    private String getCurrentUser() {
        return "";
    }


}
