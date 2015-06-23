package com.esgi.groupe1.eloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.esgi.groupe1.eloworld.adapter.ForumAdapter;
import com.esgi.groupe1.eloworld.adapter.GameAdapter;
import com.esgi.groupe1.eloworld.method.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ForumActivity extends Activity {
    private static final String url ="http://manouanachristopeher.site90.net/EloWorldWeb/Code/WebService/forum/getForumCat.php";
    ListView listView;
    ForumObjet forumObjet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        new Categories().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forum, menu);
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
    class Categories extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            JSONParser parser = new JSONParser();
            JSONObject object =parser.makeHttpRequestAPI(url);
            List<ForumObjet> mesForum = new ArrayList();
            ArrayList<String> listCat = new ArrayList();

            try {
                JSONArray array = object.getJSONArray("cat");
                for (int i = 0;i< array.length();i++){
                    JSONObject rowOfCat = array.getJSONObject(i);
                    forumObjet = new ForumObjet(rowOfCat.optString("Name"),rowOfCat.optInt("idForumCategory"));
                    mesForum.add(forumObjet);
                    listCat.add(rowOfCat.optString("idForumCategory"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Mes cat", String.valueOf(mesForum));

            return mesForum;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            ArrayList item = (ArrayList) o;
            //ArrayAdapter adapter =  new ArrayAdapter(ForumActivity.this,android.R.layout.simple_list_item_1,item);
            ListAdapter adapter = new ForumAdapter(ForumActivity.this,item);

            listView =(ListView) findViewById(R.id.list_forum);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String s = String.valueOf(parent.getItemAtPosition(position));
                    ForumObjet monF = (ForumObjet) parent.getItemAtPosition(position);
                    int idForum = monF.getIdforum();
                    Log.d("test",String.valueOf(idForum));
                    Intent intent = new Intent(ForumActivity.this,TopicsActivity.class);
                    intent.putExtra("categorie",String.valueOf(idForum));
                    startActivity(intent);

                }
            });

        }
    }


}
