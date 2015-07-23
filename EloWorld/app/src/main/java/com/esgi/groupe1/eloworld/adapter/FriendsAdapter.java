package com.esgi.groupe1.eloworld.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.groupe1.eloworld.Model.Friend;
import com.esgi.groupe1.eloworld.R;
import com.esgi.groupe1.eloworld.activity.MessengerActivity;
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

/**
 * Created by Christopher on 16/07/2015.
 */
public class FriendsAdapter extends ArrayAdapter{
    private Context context;
    private ImageView summoner;
    private TextView nameSummoner;
    private ImageView icmessage, chat;
    private List<Friend> friendsList;
    private  Friend friend;
    private HashMap user;
    private String URL_CONVERSATION ="http://manouanachristopeher.site90.net/EloWorldWeb/Code/WebService/message/conversation.php";

    private SQLiteHandler db;
    private int idFriend;

    public FriendsAdapter(Context context, List<Friend> friendsList) {
        super(context, R.layout.friend_adapter, friendsList);
        this.context = context;
        this.friendsList = friendsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        friend = friendsList.get(position);
        convertView = inflater.inflate(R.layout.friend_adapter, parent, false);
        summoner = (ImageView) convertView.findViewById(R.id.friendSquare);
        chat = (ImageView) convertView.findViewById(R.id.ic_chat);
        nameSummoner =(TextView) convertView.findViewById(R.id.friendName);
        Log.d("allFriends", String.valueOf(friend));
        final String name = friend.getName();

        Log.d("Friend",String.valueOf(idFriend));
        String url = "http://avatar.leagueoflegends.com/euw/"+String.valueOf(friend.getName())+".png";
        Picasso.with(getContext()).load(url).into(summoner);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idFriend = friend.getId();
                db = new SQLiteHandler(getContext());
                new CreateConversation().execute();
            }
        });
        nameSummoner.setText(name);
        return convertView;
    }

    class CreateConversation extends AsyncTask{
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            user = db.getUser();
            int idUser = (int) user.get("idUser");
            Log.d("un ami",String.valueOf(idFriend));
            Log.d("moi",String.valueOf(idUser));
            List<NameValuePair>nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("idUser",String.valueOf(idUser)));
            nameValuePairs.add(new BasicNameValuePair("idUserFollow",String.valueOf(idFriend)));
            JSONObject object = new JSONParser().makeHttpRequest(URL_CONVERSATION, nameValuePairs);
            try {
                JSONArray jsonArray = object.getJSONArray("conversation");
                Log.d("jsonArray",String.valueOf(jsonArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
