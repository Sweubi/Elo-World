package com.esgi.groupe1.eloworld.activity;

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
import android.widget.EditText;
import android.widget.ListView;

import com.esgi.groupe1.eloworld.Model.Message;
import com.esgi.groupe1.eloworld.R;
import com.esgi.groupe1.eloworld.adapter.MessengerAdapter;
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


public class MessengerActivity extends Activity {
    private int idConversation;
    private ListView conversationListView;
    private EditText messageEditText;
    private Button sendButton;
    private String message;
    private int iduser;
    private SQLiteHandler db;
    private HashMap<String,Object> user;
    private String url ="http://manouanachristopeher.site90.net/EloWorldWeb/Code/WebService/message/createmessage.php";
    private String url_message ="http://manouanachristopeher.site90.net/EloWorldWeb/Code/WebService/message/getMessages.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intentExtra = getIntent();
        idConversation = intentExtra.getIntExtra("idConversation",0);
        new DisplayMessage().execute();
        db = new SQLiteHandler(getApplicationContext());
        user =db.getUser();
        iduser= (Integer) user.get("idUser");
        messageEditText = (EditText) findViewById(R.id.message);
        sendButton = (Button) findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = messageEditText.getText().toString();
                new SendMessage().execute();
                new DisplayMessage().execute();
                messageEditText.setText("");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_messenger, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
    class SendMessage extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("message",message));
            nameValuePairs.add(new BasicNameValuePair("iduser",String.valueOf(iduser)));
            nameValuePairs.add(new BasicNameValuePair("idconversation",String.valueOf(idConversation)));
            JSONObject json = JSONParser.makeHttpRequest(url, nameValuePairs);
            return null;
        }
    }
    class DisplayMessage extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] params) {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("idConversation", String.valueOf(idConversation)));
            JSONObject messageObject = JSONParser.makeHttpRequest(url_message,nameValuePairs);
            ArrayList<Message> messageArrayList = new ArrayList<>();
            try {
                JSONArray messageArray = messageObject.getJSONArray("message");
                AppMethod appMethod = new AppMethod();
                for (int i=0;i<messageArray.length();i++){
                    JSONObject object = messageArray.getJSONObject(i);
                    int idMessage = object.optInt("idMessage");
                    String date = object.optString("Date");
                    String message = object.getString("MessageBody");
                    int idConversation = object.optInt("Conversation_idConversatio");
                    int idUser = object.optInt("idUser_user");
                    String Summonername = appMethod.PseudoAuthor(String.valueOf(idUser));
                    Message Unmessage = new Message(idMessage,date,message,idConversation,idUser,Summonername);
                    messageArrayList.add(Unmessage);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return messageArrayList;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            ArrayList<Message> messageArrayList = (ArrayList)o;
            ArrayAdapter adapter = new MessengerAdapter(MessengerActivity.this,messageArrayList);
            conversationListView =(ListView) findViewById(R.id.listmsg);
            conversationListView.setAdapter(adapter);


        }
    }

}
