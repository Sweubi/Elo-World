package com.esgi.groupe1.eloworld.Layout;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.esgi.groupe1.eloworld.Objects.Friend;
import com.esgi.groupe1.eloworld.R;
import com.esgi.groupe1.eloworld.method.AppMethod;
import com.esgi.groupe1.eloworld.method.JSONParser;
import com.esgi.groupe1.eloworld.sqlLite.SQLiteHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ListAllFolowActivity extends Activity {
    private ListView allFollow;
    private String url_allFriend ="http://manouanachristopeher.site90.net/EloWorldWeb/Code/WebService/follower/getFollowersList.php";
    private SQLiteHandler db;
    private HashMap<String,Object> user;
    private int myid;
    private AppMethod appMethod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_folow);
        db = new SQLiteHandler(getApplicationContext());
        user =db.getUser();
        myid = (Integer) user.get("idUser");
        new MyFriends().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_all_folow, menu);
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
    class MyFriends extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {

            List<NameValuePair> pairs = new ArrayList<>();
            pairs.add(new BasicNameValuePair("idUser1",String.valueOf(myid)));
            JSONObject object = JSONParser.makeHttpRequest(url_allFriend,pairs);
            String pseudo;
            appMethod = new AppMethod();
            List<String> friendList = new ArrayList<>();
            try {
                int success = object.getInt("success");
                JSONArray allFriends = object.getJSONArray("allFriends");
                for (int i=0;i<allFriends.length();i++){
                   JSONObject objectf = allFriends.getJSONObject(i);
                    int iduser = objectf.getInt("idFollowed");

                    pseudo = appMethod.PseudoAuthor(String.valueOf(iduser));
                    Friend friend = new Friend(pseudo,iduser);
                    friendList.add(friend.toString());

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return friendList;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            List<String> friendList = (List<String>) o;
            if (friendList.size() == 0){
                Log.d("False",String.valueOf(friendList.size()));
            }else{
                Log.d("true",String.valueOf(friendList.size()));
                ArrayAdapter adapter= new  ArrayAdapter<String>(ListAllFolowActivity.this,android.R.layout.simple_list_item_1,friendList);
                allFollow = (ListView)findViewById(R.id.followList);
                allFollow.setAdapter(adapter);
            }
        }
    }
}
