package com.esgi.groupe1.eloworld.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.squareup.picasso.Picasso;

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
        String url = "http://avatar.leagueoflegends.com/euw/"+String.valueOf(friend.getName())+".png";
        Picasso.with(getContext()).load(url).into(summoner);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MessengerActivity.class);
                context.startActivity(intent);
            }
        });
        nameSummoner.setText(name);
        return convertView;
    }
}
