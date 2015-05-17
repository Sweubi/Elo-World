package com.esgi.groupe1.eloworld;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.esgi.groupe1.eloworld.method.BitmapLruCache;
import com.esgi.groupe1.eloworld.sqlLite.SQLiteHandler;

import java.util.HashMap;


public class ProfilActivity extends Activity {
    String url ;
    SQLiteHandler  db ;
    NetworkImageView networkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String,Object> user =db.getUser();
        String pseudo = (String) user.get("pseudo");
        setTitle(pseudo);
        url = "http://avatar.leagueoflegends.com/euw/"+pseudo+".png";
        networkImageView = (NetworkImageView) findViewById(R.id.networkImageView);
        ImageLoader.ImageCache imageCache = new BitmapLruCache();
        ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(getApplicationContext()), imageCache);
        networkImageView.setImageUrl(url, imageLoader);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profil, menu);
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
}
