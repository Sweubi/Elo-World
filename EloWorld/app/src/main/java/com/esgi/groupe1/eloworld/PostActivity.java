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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.esgi.groupe1.eloworld.adapter.PostAdapter;
import com.esgi.groupe1.eloworld.appObject.Post;
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


public class PostActivity extends Activity {
    private String url_getPost ="http://manouanachristopeher.site90.net/EloWorldWeb/Code/WebService/forum/getPost.php";
    private String url_create ="http://manouanachristopeher.site90.net/EloWorldWeb/Code/WebService/forum/createPost.php";
    private  String textpost;
    private String idTopic= null;
    private ListView allPosts;
    private EditText textposEditText;
    private Button sendpost;
    private SQLiteHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Intent datapassing = getIntent();
        idTopic  = datapassing.getStringExtra("idTopic");
        db = new SQLiteHandler(getApplicationContext());
        new DisplayPost().execute();
        textposEditText = (EditText) findViewById(R.id.EdtPost);
        sendpost = (Button) findViewById(R.id.btAdd);
        sendpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textpost = textposEditText.getText().toString();
                if (!textpost.equals("")){
                    Toast.makeText(getApplicationContext(),idTopic,Toast.LENGTH_SHORT).show();
                    new CreatePost().execute();
                    new DisplayPost().execute();
                }else {
                    Toast.makeText(getApplicationContext(),"remplir le champ",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post, menu);
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
   class DisplayPost extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] params) {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("idTopic", idTopic));
            JSONObject jsonObject = JSONParser.makeHttpRequest(url_getPost,parameters);
            ArrayList<Post> posts = new ArrayList<>();;
            try {
                int success = jsonObject.getInt("success");

                    JSONArray arraypost = jsonObject.getJSONArray("post");

                    for (int i =0;i<arraypost.length();i++){
                        JSONObject unObject = arraypost.getJSONObject(i);
                        Log.d("Un Objet",String.valueOf(unObject));
                        String comment = unObject.optString("BodyPost");
                        String auteur = unObject.optString("User_idUser");
                        String Name = new AppMethod().PseudoAuthor(auteur);
                        Log.d("User",Name);
                        posts.add(new Post(Name,comment));
                    }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return posts;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            ArrayList posts = (ArrayList)o;
            ArrayAdapter adapter = new PostAdapter(PostActivity.this,posts);
            allPosts = (ListView) findViewById(R.id.allPosts);
            allPosts.setAdapter(adapter);
            allPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

        }
    }
    class CreatePost extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            List<NameValuePair> parameters = new ArrayList();
            HashMap<String,Object> user = db.getUser();
            int idUser = (int) user.get("idUser");
            parameters.add(new BasicNameValuePair("Topic",idTopic));
            parameters.add(new BasicNameValuePair("BodyPost",textpost));
            parameters.add(new BasicNameValuePair("User_idUser",String.valueOf(idUser)));
            JSONObject object = new JSONParser().makeHttpRequest(url_create, parameters);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            textposEditText.setText("");
        }
    }

}
