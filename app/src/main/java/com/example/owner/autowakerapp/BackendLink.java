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

    private String desired;
    private String result = "";

    public BackendLink(String desired){
        this.desired = desired;
    }

    // TODO: Massive patchwork is needed here.
    protected String doInBackground(URL... urls) {
        int count = urls.length;
        for (int i = 0; i < count; i++) {
            try {
                String content = readData(urls[i]);
                JSONObject obj = new JSONObject(content);
                this.result = obj.getString(this.desired);
                Log.i("Time", this.result);
                return this.result;
            } catch (org.json.JSONException e) {
                Log.e("JSON", "Result was not a JSON object." + e.toString());
                Log.i("JSON", "Url was: " + urls[i].toString());
            }
            return this.result;
        }
        return this.result;
    }

    protected void onPostExecute(String result) {
        Log.i("Result", "The result is: " + result);
    }

    public String getResult(){
        try {
            String test = doInBackground(new URL("http://192.168.56.1:8888/?date=2017/07/15&user=5T23R6"));
            Log.i("Interesting", "The result of calling doInBackground is: " + test);
        } catch (Exception e) {
            Log.e("Result", "Not sure what happened here.");
        }
        return this.result;
    }

    private String readData(URL url) {
        BufferedReader br;
        URLConnection conn;
        try {
            conn = url.openConnection();
        } catch(IOException e) {
            Log.e("Connection", "Opening the connection failed with: " + e.toString());
            return "";
        } catch (Exception e) {
            Log.e("Connection", "Unknown exception occured when connecting with: " + e.toString());
            return "";
        }
        Log.i("URL", url.toString());
        try {
            br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
        } catch(IOException e) {
            Log.e("Stream", "Opening the input stream failed with: " + e.toString());
            return "";
        } catch(Exception e) {
            Log.e("Stream", "Unknown exception occured with: " + e.toString());
            return "";
        }
        String content = "";
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                Log.i("line", "Line is: " + line);
                content += line;
            }
        } catch (IOException e) {
            Log.e("Reading", "Reading from the connection caused an error: " + e.toString());
            return content;
        } catch (Exception e) {
            Log.e("Reading", "Unknown error occured while reading with: " + e.toString());
            return content;
        }
        Log.i("Content", "Content is: " + content);
        return content;
    }



}
