package com.esgi.groupe1.eloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.esgi.groupe1.eloworld.method.Methodlogin;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class Login extends Activity {
    TextView newaccount;
    EditText inputemail,inputpassword;

    Button btLogin;
    public static final String URL_LOGIN ="http://192.168.31.1/EloWorldWeb/webservices/script_login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        newaccount = (TextView) findViewById(R.id.nouveau);
        inputemail = (EditText) findViewById(R.id.email);
        inputpassword =(EditText)findViewById(R.id.password);
        btLogin = (Button)findViewById(R.id.btnlog);


        btLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                 String email = inputemail.getText().toString();
                 String password = inputpassword.getText().toString();
                //If not empty
                if (email.trim().length() > 0 && password.trim().length() > 0){
                     //Methodlogin.loginMethod(email, password, URL_LOGIN);
                     new Logintask().execute();
                     Toast.makeText(getApplication(),"Bienvenue",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplication(),"Veuillez remplir tous les champs!",Toast.LENGTH_LONG).show();
                }

            }
        });

        newaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent account = new Intent(getApplicationContext(),Register.class);
                startActivity(account);
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class Logintask extends AsyncTask{
        String email = inputemail.getText().toString();
        String password = inputpassword.getText().toString();

        @Override
        protected Object doInBackground(Object[] params) {
            JSONObject jsonObject = new Methodlogin().loginMethod(email,password,URL_LOGIN);
            return jsonObject;
        }
    }

}
