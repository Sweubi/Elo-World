package com.esgi.groupe1.eloworld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.groupe1.eloworld.R;
import com.esgi.groupe1.eloworld.Model.Topic;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Christopher on 21/06/2015.
 */
public class TopicAdapterCustom extends ArrayAdapter{
    TextView textSubject,textAuteur,textDate;
   ImageView networkImageView;
    List<Topic>topics;
    public TopicAdapterCustom(Context context, List<Topic> topics) {
        super(context, R.layout.topic_list_adapter_custom, topics);
        this.topics=topics;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.topic_list_adapter_custom,parent,false);
        Topic unTopic = topics.get(position);
        networkImageView = (ImageView) convertView.findViewById(R.id.iconSum);
        String url = "http://avatar.leagueoflegends.com/euw/"+String.valueOf(unTopic.getAuteur())+".png";
        Picasso.with(getContext()).load(url).into(networkImageView);
        textSubject = (TextView) convertView.findViewById(R.id.sujet);
        textAuteur = (TextView) convertView.findViewById(R.id.userName);
        textDate = (TextView) convertView.findViewById(R.id.date);

        textSubject.setText("Topic : "+String.valueOf(unTopic.getSujet()));
        textAuteur.setText(String.valueOf(unTopic.getAuteur()));
        textDate.setText(String.valueOf(unTopic.getDate()));

        return  convertView;
    }
}
