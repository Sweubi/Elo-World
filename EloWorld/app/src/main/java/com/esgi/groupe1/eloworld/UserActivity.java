package com.esgi.groupe1.eloworld;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.esgi.groupe1.eloworld.adapter.TopicAdapterCustom;
import com.esgi.groupe1.eloworld.method.AppMethod;
import com.esgi.groupe1.eloworld.method.JSONParser;
import com.esgi.groupe1.eloworld.method.SessionManager;
import com.esgi.groupe1.eloworld.sqlLite.SQLiteHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class UserActivity extends Activity implements Runnable {
    private SessionManager session;
    SQLiteHandler db ;
    EditText editTextSearch;
    ImageView searchImageV;
    ListView listViewActu,mDrawerList;
    private String url_alltopic ="http://manouanachristopeher.site90.net/EloWorldWeb/Code/WebService/forum/getAllTopics.php";
    private DrawerLayout mDrawerLayout ;
    private ImageView square;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout layoutSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        layoutSearch= (LinearLayout) findViewById(R.id.linearSearch);
        layoutSearch.setVisibility(LinearLayout.GONE);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new DisplayActu().execute();
            }
        });
        thread.start();

        editTextSearch = (EditText) findViewById(R.id.searchEdit);
        searchImageV = (ImageView) findViewById(R.id.searchImageV);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn())
            logout();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,null,
                R.string.drawer_open,
                R.string.drawer_close);

        String[] mTitle = getResources().getStringArray(R.array.menu_string);
        mDrawerList.setAdapter(new ArrayAdapter<>(this,R.layout.drawer_listview_item,mTitle));
        square = (ImageView) findViewById(R.id.squareSum);
        HashMap<String,Object> user =db.getUser();
        String pseudo = (String) user.get("pseudo");
        Picasso.with(getApplicationContext()).load("http://avatar.leagueoflegends.com/euw/"+pseudo+".png").into(square);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                        startActivity(intentSettings);
                        break;
                    case 2:
                        Intent intentForum = new Intent(getApplicationContext(), ForumActivity.class);
                        startActivity(intentForum);
                        break;
                    case 3:
                        logout();
                        break;

                }

            }
        });

        searchImageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textFind = editTextSearch.getText().toString();
                Log.d("text", textFind);
                Log.d("length", String.valueOf(textFind.trim().length()));
                if (!(textFind.equals(""))) {
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    intent.putExtra("SummonerFound", textFind);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplication(), "Veuillez remplir le champ", Toast.LENGTH_LONG).show();
                }
            }
        });
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

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
        if (id == R.id.action_search){
            layoutSearch= (LinearLayout) findViewById(R.id.linearSearch);
            layoutSearch.setVisibility(LinearLayout.VISIBLE);
        }

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
        }if(id == R.id.action_logout){
           logout();
        }if (id == R.id.action_me){
            Intent intent = new Intent(getApplicationContext(),ProfilActivity.class);
            startActivity(intent);
        }if (id == R.id.action_foum){
            Intent intent = new Intent(getApplicationContext(),ForumActivity.class);
            startActivity(intent);
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

    @Override
    public void run() {
        new DisplayActu().execute();
    }

    class DisplayActu extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            List<Topic> topicList = new ArrayList<>();
            JSONObject dataObject = new JSONParser().makeHttpRequestAPI(url_alltopic);
            try {
                JSONArray dataArray = dataObject.getJSONArray("allTopics");

                for (int i =0;i<dataArray.length();i++){
                    JSONObject object = dataArray.getJSONObject(i);
                    String libelle = object.optString("Name");
                    String date = object.optString("Date");
                    String idauteur = object.optString("User_idUser");
                    String idTopic = String.valueOf(object.optInt("idTopic")) ;
                    Log.d("String Id",idTopic);
                    String name = new AppMethod().PseudoAuthor(idauteur);
                    Topic topicObject = new Topic(libelle,name,date,idTopic);
                    topicList.add(topicObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return topicList;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            ArrayList listTopics = (ArrayList)o;
            ArrayAdapter adapter = new TopicAdapterCustom(getApplicationContext(),listTopics);
            listViewActu =(ListView) findViewById(R.id.ListActu);
            listViewActu.setAdapter(adapter);
            listViewActu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Topic topic = (Topic) parent.getItemAtPosition(position);
                    String idOfTopic = String.valueOf(topic.getIdtopic());
                    Log.d("Objet topic Juste here", idOfTopic);
                    Intent intent = new Intent(getApplicationContext(),PostActivity.class);
                    intent.putExtra("idTopic",idOfTopic);
                    startActivity(intent);
                }
            });
        }
    }

}
