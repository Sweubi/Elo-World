package com.esgi.groupe1.eloworld.Parse;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by Christopher on 16/07/2015.
 */
public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "709s1UdRz7NKrJG9HQ1VUUy9VbDNrphrmC4dXMrH", "ydMBtHAHhfaOT1KdhbIIodATSrX1I5SZy7pMvAYK");
        ParseUser user = new ParseUser();
        user.setUsername("manouana");
        user.setPassword("azerty");
        user.setEmail("christophermanouana@gmail.com");

// other fields can be set just like with ParseObject


        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Log.d("Parse","Hooray! Let them use the app now.");
                } else {
                    Log.d("Parse","Sign up didn't succeed. Look at the ParseException");
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });


    }
}
