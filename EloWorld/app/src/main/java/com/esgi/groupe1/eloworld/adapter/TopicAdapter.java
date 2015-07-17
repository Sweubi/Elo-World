package com.esgi.groupe1.eloworld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.groupe1.eloworld.R;
import com.esgi.groupe1.eloworld.appObject.Topic;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Christopher on 21/06/2015.
 */
public class TopicAdapter extends ArrayAdapter{
    TextView textSubject,textAuteur,textDate;
    List<Topic>topics;
    ImageView networkImageView;
    public TopicAdapter(Context context, List<Topic>topics) {
        super(context, R.layout.topic_list_adapter, topics);
        this.topics=topics;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.topic_list_adapter,parent,false);
        Topic unTopic = topics.get(position);

        textSubject = (TextView) convertView.findViewById(R.id.sujet);
        textAuteur = (TextView) convertView.findViewById(R.id.userName);
        textDate = (TextView) convertView.findViewById(R.id.date);
        networkImageView = (ImageView) convertView.findViewById(R.id.iconSum);
        String url = "http://avatar.leagueoflegends.com/euw/"+String.valueOf(unTopic.getAuteur())+".png";
        Picasso.with(getContext()).load(url).into(networkImageView);

        textSubject.setText(String.valueOf(unTopic.getSujet()));
        textAuteur.setText(String.valueOf(unTopic.getAuteur()));
        textDate.setText(String.valueOf(unTopic.getDate()));

        return  convertView;
    }
}
