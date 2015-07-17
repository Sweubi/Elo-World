package com.esgi.groupe1.eloworld;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.esgi.groupe1.eloworld.method.JSONParser;
import com.esgi.groupe1.eloworld.method.SessionManager;
import com.esgi.groupe1.eloworld.sqlLite.SQLiteHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SettingsActivity extends Activity {
    private SessionManager session;
    private ListView listView;
    private SQLiteHandler db;
    private HashMap<String,Object> user;
    private String url_delete ="http://manouanachristopeher.site90.net/EloWorldWeb/Code/WebService/divers/delete.php";
    private Button buttonD;
    private final Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
        user =db.getUser();
        String pseudo = (String) user.get("pseudo");
        String email = (String) user.get("email");
        String setPwd = "Modifier votre mot de passe";

        String value = null;
        final String[] values = new String[]{pseudo,email,setPwd,getResources().getString(R.string.logout)};
        for (int i =0;i<values.length;i++){
           value = values[i];
        }
        Log.d("values",value);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,values);
        listView = (ListView)findViewById(R.id.settingslist);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Maposition = String.valueOf(parent.getItemAtPosition(position));
                Log.d("id",String.valueOf(id));
                Log.d("value",String.valueOf(Maposition));
                switch ((int) id){
                    case 0:
                        Log.d("test","0");
                        break;
                    case 1:
                        Log.d("test","1");
                        break;
                    case 2:
                        Log.d("test","0");
                        break;
                    case 3:
                        Log.d("test","0");
                        logout();
                        break;
                }



            }
        });
        buttonD = (Button) findViewById(R.id.deleteBt);
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new  AlertDialog.Builder(context)
                        .setTitle("Remarque")
                        .setMessage("Veux-tu vraiment supprimer ton compte?")
                        .setPositiveButton("Oui, supprimer mon compte", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new DeleteUser().execute();
                            }
                        }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

            }
        });


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
    class DeleteUser extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {

            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("idUser",String.valueOf(user.get("idUser"))));
            Log.d("lool",String.valueOf(user.get("idUser")));
            JSONObject object = new JSONParser().makeHttpRequest(url_delete, parameters);
            return object;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            logout();
            Toast.makeText(getApplicationContext(),"Bay",Toast.LENGTH_SHORT).show();
        }
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
