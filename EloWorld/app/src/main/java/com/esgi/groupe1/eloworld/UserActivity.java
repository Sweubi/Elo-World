package com.esgi.groupe1.eloworld;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.groupe1.eloworld.RiotGameAPI.APIMethod;
import com.esgi.groupe1.eloworld.method.SessionManager;
import com.esgi.groupe1.eloworld.sqlLite.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class UserActivity extends Activity  {
    private SessionManager session;
    SQLiteHandler db ;

    EditText editTextSearch;
    ImageView searchImageV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        editTextSearch = (EditText) findViewById(R.id.searchEdit);
        searchImageV = (ImageView) findViewById(R.id.searchImageV);
        //editTextSearch.setVisibility(View.GONE);
        db = new SQLiteHandler(getApplicationContext());


        // session manager
        session = new SessionManager(getApplicationContext());
        //progressBar =(ProgressBar)findViewById(R.id.progressbar);

        if (!session.isLoggedIn())
            logout();


        searchImageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textFind = editTextSearch.getText().toString();
                Log.d("text",textFind);
                Log.d("length",String.valueOf(textFind.trim().length()));
                if (!(textFind.equals(""))){
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    intent.putExtra("SummonerFound",textFind);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplication(), "Veuillez remplir le champ", Toast.LENGTH_LONG).show();
                }
            }
        });
        ActionBar actionBar = getActionBar();
        //diviser l'actionBar en 2

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        /*actionBar.hide();*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();

        getMenuInflater().inflate(R.menu.menu_user, menu);


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
            Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
        }if(id == R.id.action_logout){
           logout();
        }if (id == R.id.action_me){
            Intent intent = new Intent(getApplicationContext(),ProfilActivity.class);
            startActivity(intent);
        }/*if (id == R.id.action_settings){
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        session.setLogin(false);
        db.deleteUsers();
        // Launching the login activity
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

}
