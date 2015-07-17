package com.esgi.groupe1.eloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.esgi.groupe1.eloworld.method.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends Activity {
    public static final String  url ="http://manouanachristopeher.site90.net/EloWorldWeb/Code/WebService/divers/getUser.php";
    Intent intent;
    TextView textView;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        textView = (TextView) findViewById(R.id.nodata);
        new SearchSummoner().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
    class SearchSummoner extends AsyncTask{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected JSONObject doInBackground(Object[] params) {
            intent = getIntent();
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            String pseudo  = (String) intent.getSerializableExtra("SummonerFound");
            parameters.add(new BasicNameValuePair("pseudo", pseudo));
            JSONObject object = new JSONParser().makeHttpRequest(url, parameters);
            return object;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            JSONObject objet =(JSONObject) o;
            try {
                int success = objet.getInt("success");
                if (success ==0){
                    String message = objet.getString("message");
                    textView.setText(message);

                }else {

                    JSONArray array = objet.getJSONArray("user");
                    ArrayList<String> strings= new ArrayList<String>();
                    ArrayList<Integer> id = new ArrayList<Integer>();
                    for (int i =0;i<array.length();i++ ){
                        JSONObject data = array.getJSONObject(i);
                        String pseudo  = data.getString("pseudo");
                        int idUser = data.optInt("idUser");
                        id.add(idUser);
                        strings.add(pseudo);
                    }

                    ListAdapter listAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1,strings);
                    listView = (ListView)findViewById(R.id.listSummoner);
                    listView.setAdapter(listAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           String name = String.valueOf(parent.getItemAtPosition(position));
                           Toast.makeText(SearchActivity.this,name,Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(getApplicationContext(),FriendActivity.class);
                           intent.putExtra("Summoner",name);
                           startActivity(intent);
                           finish();
                       }
                   });

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}
