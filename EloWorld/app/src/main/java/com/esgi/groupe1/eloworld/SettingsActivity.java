package com.esgi.groupe1.eloworld;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.esgi.groupe1.eloworld.sqlLite.SQLiteHandler;

import java.util.HashMap;
import java.util.List;


public class SettingsActivity extends Activity {
    ListView listView;
    SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String,Object> user =db.getUser();
        String pseudo = (String) user.get("pseudo");
        String email = (String) user.get("email");
        String value = null;
        String[] values = new String[]{pseudo,email};
        for (int i =0;i<values.length;i++){
           value = values[i];
        }
        Log.d("values",value);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,values);
        listView = (ListView)findViewById(R.id.settingslist);
        listView.setAdapter(arrayAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
