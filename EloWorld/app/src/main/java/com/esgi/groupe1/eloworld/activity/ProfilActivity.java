package com.esgi.groupe1.eloworld.activity;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.esgi.groupe1.eloworld.R;
import com.esgi.groupe1.eloworld.RiotGameAPI.APIMethod;
import com.esgi.groupe1.eloworld.adapter.GameAdapter;
import com.esgi.groupe1.eloworld.Model.Games;
import com.esgi.groupe1.eloworld.method.JSONParser;
import com.esgi.groupe1.eloworld.sqlLite.SQLiteHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfilActivity extends Activity  {
    private String url ;
    private String url_game ="http://christophermanouana.fr/ew/jsontest.php";
    private SQLiteHandler  db ;
    private ImageView networkImageView;
    private ImageView splash;
    private ListView listView;
    private ProgressDialog dialog;
    private TextView textViewname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String,Object> user =db.getUser();
        
        new Historique().execute();
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ic_action_person);

        String pseudo = (String) user.get("pseudo");
        setTitle(pseudo);
        networkImageView = (ImageView) findViewById(R.id.networkImageView);
        Picasso.with(getApplicationContext()).load("http://avatar.leagueoflegends.com/euw/"+pseudo+".png").into(networkImageView);

        textViewname = (TextView) findViewById(R.id.mysummname);
        textViewname.setText(pseudo);



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
        Intent intent;
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_friends:
                intent = new Intent(getApplicationContext(), ListAllFolowActivity.class);
                intent.putExtra("provenance","friend");
                startActivity(intent);
                break;
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }


    class Historique extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ProfilActivity.this);
            dialog.setMessage("Hello...");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected List doInBackground(Object[] params) {
            APIMethod apiMethod =new APIMethod();
            HashMap user = db.getUser();

            String server = (String)user.get("server");
            int IdSummoner = (int)user.get("IdSummoner");
            Log.d("data",String.valueOf(server)+String.valueOf(IdSummoner));
            List<NameValuePair> nameValuePairList = new ArrayList();
            nameValuePairList.add(new BasicNameValuePair("summoner",String.valueOf(IdSummoner)));
            nameValuePairList.add(new BasicNameValuePair("server",server));


            //ArrayList array = (ArrayList) apiMethod.SummonerGame(IdSummoner, server);

            List<Games> mesgames = new ArrayList<Games>();
            JSONObject object = JSONParser.makeHttpRequest(url_game,nameValuePairList);
            try {
                JSONArray arrayObject = object.getJSONArray("arrayGames");
                //Log.d("Lenght",String.valueOf(arrayObject.length()));
                for (int i =0;i<arrayObject.length();i++){
                    JSONObject getdata = arrayObject.getJSONObject(i);
                    //Log.d("Object",String.valueOf(i));
                    String champion = getdata.optString("champion");
                    int idSpell1 = getdata.getInt("spell1");
                    int idSpell2 = getdata.getInt("spell2");
                    boolean wingame = getdata.getBoolean("win");
                    int numDeaths = getdata.optInt("numDeaths");
                    int championsKilled = getdata.optInt("championsKilled");
                    int championsAssists = getdata.optInt("assists");
                    int idItem0 = getdata.optInt("item0");
                    int idItem1 = getdata.optInt("item1");
                    int idItem2 = getdata.optInt("item2");
                    int idItem3 = getdata.optInt("item3");
                    int idItem4 = getdata.optInt("item4");
                    int idItem5 = getdata.optInt("item5");
                    int idItem6 = getdata.optInt("item6");

                    Log.d("Win",String.valueOf(wingame));
                    JSONObject object1Spell = apiMethod.summonerSpell(idSpell1);
                    JSONObject object1Spel2 = apiMethod.summonerSpell(idSpell2);
                    String spell1 = object1Spell.getString("key");
                    String spell2 = object1Spel2.getString("key");
                    mesgames.add(new Games(champion, spell1, spell2, championsKilled, championsAssists, numDeaths, wingame,idItem0, idItem1, idItem2,idItem3, idItem4, idItem5,idItem6));


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /*try {
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
            }*/
            return mesgames;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (dialog.isShowing())
                dialog.dismiss();
            List mesgames = (List)o;
            ArrayAdapter adapter = new GameAdapter(ProfilActivity.this,mesgames);
            listView = (ListView) findViewById(R.id.list_matches);
            listView.setAdapter(adapter);
            //last champ played
            Games uneGame = (Games) mesgames.get(0);
            String lastChamp = uneGame.getChampion();
            //splash = (ImageView) findViewById(R.id.splashArt);
           // Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+lastChamp+"_0.jpg").into(splash);
        }
    }



}
