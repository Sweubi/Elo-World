package com.esgi.groupe1.eloworld.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.groupe1.eloworld.R;
import com.esgi.groupe1.eloworld.adapter.TopicAdapterCustom;
import com.esgi.groupe1.eloworld.Model.Topic;
import com.esgi.groupe1.eloworld.method.AppMethod;
import com.esgi.groupe1.eloworld.method.JSONParser;
import com.esgi.groupe1.eloworld.method.SessionManager;
import com.esgi.groupe1.eloworld.sqlLite.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class UserActivity extends Activity  {
    private SessionManager session;
    SQLiteHandler db ;
    EditText editTextSearch;
    ImageView searchImageV;
    ListView listViewActu;
    private String url_alltopic ="http://manouanachristopeher.site90.net/EloWorldWeb/Code/WebService/forum/getAllTopics.php";
    private LinearLayout layoutSearch;
    private TextView viewnothing;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        layoutSearch= (LinearLayout) findViewById(R.id.linearSearch);
        layoutSearch.setVisibility(LinearLayout.GONE);
        editTextSearch = (EditText) findViewById(R.id.searchEdit);
        searchImageV = (ImageView) findViewById(R.id.searchImageV);
        viewnothing = (TextView) findViewById(R.id.nothing);
        new DisplayActu().execute();
        /*Timer minuteur = new Timer();
        TimerTask tache = new TimerTask() {
            public void run() {
                new DisplayActu().execute();
            }
        };
        minuteur.schedule(tache, 7, 10000);*/
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn())
            logout();
        if(isNetworkAvailable()) {

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
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
            swipeRefreshLayout.setProgressBackgroundColor(R.color.teal_500);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new DisplayActu().execute();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
            /*swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    new DisplayActu().execute();
                }
            });*/
        }else {
            logout();
            Toast.makeText(getApplicationContext(),"Aucune connexion",Toast.LENGTH_SHORT).show();
        }
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
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
        }if (id == R.id.action_me){
            Intent intent = new Intent(getApplicationContext(),ProfilActivity.class);
            startActivity(intent);
        }if (id == R.id.action_foum){
            Intent intent = new Intent(getApplicationContext(),ForumActivity.class);
            startActivity(intent);
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
            if (listTopics.size() ==0){
                viewnothing.setText("aucun topic soyez le premier.");
            }else {
                ArrayAdapter adapter = new TopicAdapterCustom(getApplicationContext(), listTopics);
                listViewActu = (ListView) findViewById(R.id.ListActu);
                listViewActu.setAdapter(adapter);
                listViewActu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Topic topic = (Topic) parent.getItemAtPosition(position);
                        String idOfTopic = String.valueOf(topic.getIdtopic());
                        Log.d("Objet topic Juste here", idOfTopic);
                        Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                        intent.putExtra("idTopic", idOfTopic);
                        startActivity(intent);
                    }
                });
            }
        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
