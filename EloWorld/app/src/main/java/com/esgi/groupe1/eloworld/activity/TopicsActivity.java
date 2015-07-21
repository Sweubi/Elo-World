package com.esgi.groupe1.eloworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.esgi.groupe1.eloworld.R;
import com.esgi.groupe1.eloworld.adapter.TopicAdapter;
import com.esgi.groupe1.eloworld.Model.Topic;
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


public class TopicsActivity extends Activity {
    LinearLayout linearLayout;
    private EditText textTopic;
    private Button button;
    private String topicName;
    private SQLiteHandler db;
    private ListView maListView;
    private static final String url ="http://manouanachristopeher.site90.net/EloWorldWeb/Code/WebService/forum/createTopic.php";
    private static final String url_getTopic ="http://manouanachristopeher.site90.net/EloWorldWeb/Code/WebService/forum/getTopic.php";
    String idcategory = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);
        Intent datapassing = getIntent();
        idcategory  = datapassing.getStringExtra("categorie");
        new DisplayTopic().execute();
        linearLayout =(LinearLayout) findViewById(R.id.lTopics);
        linearLayout.setVisibility(LinearLayout.GONE);
        textTopic = (EditText)findViewById(R.id.description);
        button = (Button) findViewById(R.id.btAdd);
        db = new SQLiteHandler(getApplicationContext());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicName = textTopic.getText().toString();
                if (!topicName.equals("")){
                    new AddTopic().execute();
                    new DisplayTopic().execute();
                    linearLayout.setVisibility(LinearLayout.GONE);
                }else {
                    Toast.makeText(getApplicationContext(),"Veuillez remplire le champs",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.reload) {
            new DisplayTopic().execute();
        }if (id == R.id.editTopic) {
            linearLayout =(LinearLayout) findViewById(R.id.lTopics);
            linearLayout.setVisibility(LinearLayout.VISIBLE);
        }

        return super.onOptionsItemSelected(item);
    }
    class AddTopic extends AsyncTask{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Object doInBackground(Object[] params) {
            Intent datapassing = getIntent();
            String categorie  = datapassing.getStringExtra("categorie");
            HashMap<String,Object> user = db.getUser();
            String idUser =String.valueOf(user.get("idUser"));
            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("ForumCategory",categorie));
            parameters.add(new BasicNameValuePair("Name",topicName));
            parameters.add(new BasicNameValuePair("User_idUser", idUser));
            JSONObject object = new JSONParser().makeHttpRequest(url, parameters);
            return null;

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            textTopic.setText("");
        }
    }
    class DisplayTopic extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("idForumCategory", idcategory));
            JSONObject objectTopic = new JSONParser().makeHttpRequest(url_getTopic, parameters);
            Log.d("Le Json des topics",String.valueOf(objectTopic));
           List<Topic> topics = new ArrayList<>();
            try {
                JSONArray array = objectTopic.getJSONArray("topics");
                Log.d("Arr", String.valueOf(array));
                for (int i=0;i<array.length();i++){
                    JSONObject object = array.getJSONObject(i);
                    String idTopic =String.valueOf(object.optInt("idTopic"));
                    Log.d("idTopic", idTopic);
                    String libelle = object.optString("Name");
                    String date = object.optString("Date");
                    String auteur = object.optString("User_idUser");
                    String test = new AppMethod().PseudoAuthor(auteur);
                    Topic topicObject = new Topic(libelle,test,date,idTopic);
                    topics.add(topicObject);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return topics;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            ArrayList item = (ArrayList) o;
            ListAdapter adapter = new TopicAdapter(TopicsActivity.this,item);
            maListView = (ListView) findViewById(R.id.allTopics);
            maListView.setAdapter(adapter);
            maListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
