package com.example.owner.autowakerapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.*;

/**
 * Created by Owner on 7/9/2017.
 */

public class BackendLink extends AsyncTask<URL, Integer, String> {

    private String desired_;
    private String url_returned_result_ = "";
    private String WEBSITE_URL = "http://192.168.56.1:8888/";

    public BackendLink(String desired) {
        this.desired_ = desired;
    }

    protected String doInBackground(URL... urls) {
        int count = urls.length;
        for (int i = 0; i < count; i++) {
            try {
                String content = readData(urls[i]);
                JSONObject jsonObject = new JSONObject(content);
                this.url_returned_result_ = jsonObject.getString(this.desired_);
                Log.i("Time", "Time is: " + this.url_returned_result_);
                return this.url_returned_result_;
            } catch (org.json.JSONException e) {
                Log.e("JSON", "The information returned from the URL was not a JSON object. Info:" + e.toString());
            }
        }
        return this.url_returned_result_;
    }

    protected void onPostExecute(String result) {
        Log.i("Time", "The resulting time is: " + result);
    }

    public String getResult() {
        return this.url_returned_result_;
    }

    public void refreshResult() {
        try {
            String url_returned_result_ = doInBackground(new URL(retrieveFullURL()));
            Log.i("Time", "The result of calling doInBackground is: " + url_returned_result_);
        } catch (Exception e) {
            Log.e("Time", "Unknown exception occurred while executing in the background.");
        }
    }

    private String readData(URL url) {
        BufferedReader bufferedReader;
        URLConnection connection;
        try {
            connection = url.openConnection();
        } catch (IOException e) {
            Log.e("Read", "Opening the connection failed with: " + e.toString());
            return "";
        } catch (Exception e) {
            Log.e("Read", "Unknown exception occurred when connecting with: " + e.toString());
            return "";
        }
        Log.i("Time", "URL is: " + url.toString());
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            Log.e("Read", "Opening the input stream failed with: " + e.toString());
            return "";
        } catch (Exception e) {
            Log.e("Read", "Unknown exception occurred with: " + e.toString());
            return "";
        }
        StringBuilder content = new StringBuilder();
        String line = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            Log.e("Read", "Reading from the connection caused an error: " + e.toString());
        } catch (Exception e) {
            Log.e("Read", "Unknown error occurred while reading with: " + e.toString());
        }
        Log.i("Read", "Content from URL " + url + " is: " + content);
        return content.toString();
    }

    private String retrieveFullURL() {
        // TODO: Assemble appropriate link and parameters as a URL object, not string ;L
        String date = getCurrentDate();
        String current_user = getCurrentUser();
        //Path filePath = Paths.get(WEBSITE_URL, "sleep", current_user, date);
        //return filePath.toString();
        return WEBSITE_URL + "?date=" + date + "&user=" + current_user;
    }

    private String getCurrentDate() {
        DateFormat date_format = new SimpleDateFormat("yyyy/MM/dd");
        return date_format.format(new Date());
    }

    private String getCurrentUser() {
        // TODO: Extract user from some config
        return "5T23R6";
    }
}
