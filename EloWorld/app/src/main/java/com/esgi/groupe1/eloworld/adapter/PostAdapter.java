package com.esgi.groupe1.eloworld.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.groupe1.eloworld.Post;
import com.esgi.groupe1.eloworld.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Christopher on 24/06/2015.
 */
public class PostAdapter extends ArrayAdapter {
    List<Post>postList;
    TextView summoner,commentaire;
    public PostAdapter(Context context,  List<Post>postList) {
        super(context, R.layout.list_post_adapter, postList);
        this.postList = postList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.list_post_adapter,parent,false);
        summoner =(TextView) convertView.findViewById(R.id.sumonner);
        commentaire =(TextView) convertView.findViewById(R.id.lepost);
        Post unPost = postList.get(position);
        summoner.setText(String.valueOf(unPost.getUserName()));
        commentaire.setText(unPost.getTextPost());
        ImageView imageView =(ImageView) convertView.findViewById(R.id.squareSum);
        Picasso.with(getContext()).load("http://avatar.leagueoflegends.com/euw/natsudate.png").into(imageView);


        return convertView;
    }
}
