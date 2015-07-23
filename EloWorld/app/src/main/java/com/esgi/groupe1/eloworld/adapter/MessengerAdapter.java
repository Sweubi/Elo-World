package com.esgi.groupe1.eloworld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.groupe1.eloworld.Model.Message;
import com.esgi.groupe1.eloworld.R;
import com.esgi.groupe1.eloworld.sqlLite.SQLiteHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Christopher on 14/07/2015.
 */
public class MessengerAdapter extends ArrayAdapter {
    private TextView messageTextView;
    private ArrayList<Message> messageArrayList;
    private int iduserFrom,Myiduser;
    private SQLiteHandler db;
    private HashMap<String,Object> user;
    private ImageView imageViewmsg;

    public MessengerAdapter(Context context, ArrayList<Message> messageArrayList) {
        super(context, R.layout.messenger_send, messageArrayList);
        this.messageArrayList = messageArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = messageArrayList.get(position);
        iduserFrom = message.getIdUser();
        db = new SQLiteHandler(getContext());
        user =db.getUser();
        Myiduser= (Integer) user.get("idUser");
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (iduserFrom == Myiduser){
            convertView =inflater.inflate(R.layout.messenger_send,parent,false);
        }else{
            convertView =inflater.inflate(R.layout.messenger_received,parent,false);
        }
        messageTextView = (TextView) convertView.findViewById(R.id.themessage);
        messageTextView.setText(message.getMessage());
        imageViewmsg = (ImageView) convertView.findViewById(R.id.iconSumchat);
        Picasso.with(getContext()).load("http://avatar.leagueoflegends.com/euw/" +String.valueOf(message.getSummonername())+ ".png").into(imageViewmsg);

        return  convertView;
    }
}
