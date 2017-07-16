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
        BufferedReader br = null;
        long totalSize = 0;
        for (int i = 0; i < count; i++) {
            Log.i("URL", "next url is: " + urls[i].toString());
            try {
                URLConnection conn = urls[i].openConnection();
                Log.i("URL", urls[i].toString());
                br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String content = "";
                String line = "";
                while((line = br.readLine()) != null) {
                    Log.i("line", "Line is: " + line);
                    content += line;
                }
                Log.i("Content", "Content is: " + content);
                JSONObject obj = new JSONObject(content);
                this.result = obj.getString(this.desired);
                Log.i("Time", this.result);
                br.close();
                return this.result;
            } catch (IOException e) {
                Log.e("URL", "Opening URL failed." + e.toString());
            } catch (org.json.JSONException e) {
                Log.e("URL", "Result was not a JSON object." + e.toString());
                Log.i("URL", "Url was: " + urls[i].toString());
            }
            try {
                br.close();
            } catch (Exception e) {
                Log.i("Interesting...", "Couldn't close the reader.");
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



}
