package com.esgi.groupe1.eloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.esgi.groupe1.eloworld.RiotGameAPI.APIMethod;
import com.esgi.groupe1.eloworld.adapter.GameAdapter;
import com.esgi.groupe1.eloworld.appObject.Games;
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


public class FriendActivity extends Activity {
    private TextView textView;
    private ImageView squareImageView;
    private String pseudo = null;
    private String url = "http://manouanachristopeher.site90.net/EloWorldWeb/Code/WebService/divers/getUnUser.php";
    private String urladd = "http://manouanachristopeher.site90.net/EloWorldWeb/Code/WebService/follower/addFollower.php";
    private ListView listView;
    private Button buttonFollow;
    private SQLiteHandler db;
    private HashMap<String,Object> user;
    private Integer myid = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        squareImageView = (ImageView) findViewById(R.id.squareFriend);
        textView =(TextView) findViewById(R.id.friendName);
        Intent monIntent = getIntent();
        pseudo =monIntent.getStringExtra("Summoner");
        textView.setText(pseudo);
        Picasso.with(getApplicationContext()).load("http://avatar.leagueoflegends.com/euw/"+monIntent.getStringExtra("Summoner")+".png").into(squareImageView);
        db = new SQLiteHandler(getApplicationContext());
        user =db.getUser();
        myid = (Integer) user.get("idUser");
        buttonFollow =(Button) findViewById(R.id.btfollow);
        buttonFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Follow().execute();
            }
        });
        new HistoriqueFriend().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend, menu);
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
    class HistoriqueFriend extends AsyncTask{
        APIMethod apiMethod =new APIMethod();
        List<Games> mesgames = new ArrayList<Games>();
        @Override
        protected Object doInBackground(Object[] params) {
            List<NameValuePair> pairs = new ArrayList<>();
            pairs.add(new BasicNameValuePair("pseudo", pseudo));
            JSONObject jsonObject = JSONParser.makeHttpRequest(url, pairs);
            int idSummoner = jsonObject.optInt("summonerIds");
            String server = jsonObject.optString("server");
            JSONObject object = apiMethod.getGames(idSummoner, server);
            try {
                JSONArray gamesArray = object.getJSONArray("games");
                int lenght = gamesArray.length();//10
                for (int i=0; i<lenght;i++){
                    JSONObject getall = gamesArray.getJSONObject(i);
                    Log.d("Object chris", String.valueOf(getall));
                    int idchamp = getall.getInt("championId");
                    int idSpell1 = getall.getInt("spell1");
                    int idSpell2 = getall.getInt("spell2");
                    JSONObject stats = getall.getJSONObject("stats");
                    int championsKilled = stats.optInt("championsKilled");
                    int championsAssists = stats.optInt("assists");
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
            List listgame = (ArrayList)o;
            ArrayAdapter adapter = new GameAdapter(getApplicationContext(),listgame);
            listView = (ListView) findViewById(R.id.gamesFriend);
            listView.setAdapter(adapter);
            Log.d("STP", String.valueOf(listgame));
        }
    }
    class Follow extends AsyncTask{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Object doInBackground(Object[] params) {
            List<NameValuePair> parametres = new ArrayList();
            parametres.add(new BasicNameValuePair("pseudo", pseudo));
            JSONObject jsonObject= JSONParser.makeHttpRequest(url,parametres);
            JSONObject object = null;
            try {
                int idUserToFollow  = jsonObject.getInt("idUser");
                List<NameValuePair> nameValuePairsParameters = new ArrayList<>();
                Log.d("idUser1",String.valueOf(myid));
                Log.d("idUser2",String.valueOf(idUserToFollow));
                nameValuePairsParameters.add(new BasicNameValuePair("idUser1", String.valueOf(myid)));
                nameValuePairsParameters.add(new BasicNameValuePair("idUser2",String.valueOf(idUserToFollow)));
                object = JSONParser.makeHttpRequest(urladd,nameValuePairsParameters);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("LogO",String.valueOf(object));
            return object;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }
}
