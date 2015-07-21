package com.esgi.groupe1.eloworld.Parse;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;


/**
 * Created by Christopher on 16/07/2015.
 */
public class ParseApplication extends Application {
    //id and client key from Parse
    public final static String applicationID ="G5Z4pntHjNiCerNmP2Vdn0mmSY8dpPdE3A15H4Xz";
    public final static String clientKey ="5zTxToD08G4bCImZat2Ht2KCb2nVghYAlloctEgI";
    @Override
    public void onCreate() {
        super.onCreate();
        // initialize parse with your application id and client key
        //Parse.enableLocalDatastore(this);
        Parse.initialize(this, applicationID, clientKey);

    }
}
