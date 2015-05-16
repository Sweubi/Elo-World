package com.esgi.groupe1.eloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.groupe1.eloworld.method.SessionManager;
import com.esgi.groupe1.eloworld.sqlLite.SQLiteHandler;

import java.util.HashMap;


public class UserActivity extends Activity {
    private SessionManager session;
    SQLiteHandler db ;
    ImageView profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        // session manager
        session = new SessionManager(getApplicationContext());
        //progressBar =(ProgressBar)findViewById(R.id.progressbar);
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String,Object> user =db.getUser();
        String pseudo = (String) user.get("email");
        if (!session.isLoggedIn())
            logout();

        /*Intent intent = getIntent();
        String pseudo = intent.getStringExtra("Pseudo");*/
        setTitle(pseudo);
        profil = (ImageView) findViewById(R.id.profil);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ProfilActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            return true;
        }if(id == R.id.action_logout){
           logout();
        }

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
