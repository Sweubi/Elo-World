package com.esgi.groupe1.eloworld.method;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONObject;


import java.io.InputStream;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher on 22/03/2015.
 */
public class Methodlogin  {

    public static String loginMethod(final String email, final String password, final String url){
        InputStream is = null;
        String result = "";

        //List of parameters
        List<NameValuePair>nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("password", password));

        //Send http post request
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            Log.d("url",url);

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            Log.d("entity", String.valueOf(response));
            is = entity.getContent();
            Log.d("log_tag", "http connection " + url+ " "+email);
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }



        // return JSON String
        return result;

    }
}
