package com.esgi.groupe1.eloworld;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.esgi.groupe1.eloworld.RiotGameAPI.APIMethod;
import com.esgi.groupe1.eloworld.adapter.GameAdapter;
import com.esgi.groupe1.eloworld.method.BitmapLruCache;
import com.esgi.groupe1.eloworld.sqlLite.SQLiteHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class ProfilActivity extends Activity  {
    String url ;
    SQLiteHandler  db ;
    NetworkImageView networkImageView;
    ListView listView;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ic_action_person);
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String,Object> user =db.getUser();
        String pseudo = (String) user.get("pseudo");
        setTitle(pseudo);
        url = "http://avatar.leagueoflegends.com/euw/"+pseudo+".png";
        networkImageView = (NetworkImageView) findViewById(R.id.networkImageView);
        ImageLoader.ImageCache imageCache = new BitmapLruCache();
        ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(getApplicationContext()), imageCache);
        networkImageView.setImageUrl(url, imageLoader);
        new Historique().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
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


    class Historique extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*dialog = new ProgressDialog(ProfilActivity.this);
            dialog.setMessage("Chargement en cours...");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();*/
        }

        @Override
        protected List doInBackground(Object[] params) {
            APIMethod apiMethod =new APIMethod();
            HashMap user = db.getUser();

            String server = (String)user.get("server");
            int IdSummoner = (int)user.get("IdSummoner");

            JSONObject object = apiMethod.getGames();

            List<Games> mesgames = new ArrayList<Games>();
            try {
                JSONArray gamesArray = object.getJSONArray("games");
                int lenght = gamesArray.length();//10
                for (int i=0; i<lenght;i++){
                    JSONObject getall = gamesArray.getJSONObject(i);
                    int idchamp = getall.getInt("championId");
                    int idSpell1 = getall.getInt("spell1");
                    int idSpell2 = getall.getInt("spell2");
                    JSONObject stats = getall.getJSONObject("stats");
                    int championsKilled = stats.optInt("championsKilled");
                    int championsAssists = stats.getInt("assists");
                    boolean wingame = stats.getBoolean("win");
                    int numDeaths = stats.optInt("numDeaths");
                    int idItem0 = stats.optInt("item0");
                    int idItem1 = stats.optInt("item1");
                    int idItem2 = stats.optInt("item2");
                    int idItem3 = stats.optInt("item3");
                    int idItem4 = stats.optInt("item4");
                    int idItem5 = stats.optInt("item5");
                    int idItem6 = stats.optInt("item6");
                    JSONObject objectDataChamp = apiMethod.championInfo(idchamp);
                    String champion = objectDataChamp.optString("name");
                    JSONObject object1Spell = apiMethod.summonerSpell(idSpell1);
                    JSONObject object1Spel2 = apiMethod.summonerSpell(idSpell2);
                    String spell1 = object1Spell.getString("key");
                    String spell2 = object1Spel2.getString("key");
                    mesgames.add(new Games(champion, spell1, spell2, championsKilled, championsAssists, numDeaths, wingame,idItem0, idItem1, idItem2,idItem3, idItem4, idItem5,idItem6));
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return mesgames;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //dialog.dismiss();
            List mesgames = (List)o;
            ListAdapter listAdapter = new GameAdapter(ProfilActivity.this,mesgames);
            listView = (ListView) findViewById(R.id.list_matches);
            listView.setAdapter(listAdapter);
        }
    }


}
