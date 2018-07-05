package com.example.acer.zonghe;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by acer on 2018/7/1.
 */

public class HttpUtil {
    private HttpUtilListenr httpUtilListenr;
    private static HttpUtil httpUtil;
    public static HttpUtil getInstence(){
        if (httpUtil == null){
            httpUtil = new HttpUtil();
       }
       return httpUtil;
    }

    public void getData(String url){
        MyAsynctask myAsynctask = new MyAsynctask();
        myAsynctask.execute(url);
    }

    class MyAsynctask extends AsyncTask<String ,Void ,String>{
        String json = "";
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == 200){
                    InputStream inputStream = urlConnection.getInputStream();
                    json = getStream(inputStream);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            httpUtilListenr.getDataJson(s);
        }
    }

    private String getStream(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String s = "";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while((s = reader.readLine())!=null){
                stringBuilder.append(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    interface HttpUtilListenr{
        void getDataJson(String json);
    }

    public void setHttpUtilListenr (HttpUtilListenr httpUtilListenr){
        this.httpUtilListenr = httpUtilListenr;
    }
}
