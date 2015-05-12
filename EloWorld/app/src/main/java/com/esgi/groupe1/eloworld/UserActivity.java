package com.esgi.groupe1.eloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.groupe1.eloworld.method.SessionManager;


public class UserActivity extends Activity {
    private SessionManager session;
    //private ProgressBar progressBar;
    //private int mProgressStatus = 0;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // session manager
        session = new SessionManager(getApplicationContext());
        //progressBar =(ProgressBar)findViewById(R.id.progressbar);

        if (!session.isLoggedIn())
            logout();

      /*new Thread(new Runnable() {
          @Override
          public void run() {
              while (mProgressStatus<100){
                 mHandler.post(new Runnable() {
                     @Override
                     public void run() {
                         progressBar.setProgress(mProgressStatus);

                     }
                 });
                  mProgressStatus ++;
                  android.os.SystemClock.sleep(1000);
              }
          }
      }).start();*/

        Intent intent = getIntent();
        String pseudo = intent.getStringExtra("Pseudo");
        setTitle(pseudo);

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



        // Launching the login activity
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
