package com.esgi.groupe1.eloworld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.esgi.groupe1.eloworld.activity.ForumObjet;
import com.esgi.groupe1.eloworld.R;

import java.util.List;

/**
 * Created by Christopher on 21/06/2015.
 */
public class ForumAdapter extends ArrayAdapter {
    private List<ForumObjet> forumObjets;
    TextView idf,tnameForum;
    public ForumAdapter(Context context, List<ForumObjet> forumObjets) {
        super(context, R.layout.forum_adapter_layout, forumObjets);
        this.forumObjets = forumObjets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.forum_adapter_layout,parent,false);
        ForumObjet categories = forumObjets.get(position);
        idf=(TextView) convertView.findViewById(R.id.idForum);
        tnameForum=(TextView) convertView.findViewById(R.id.nameForum);

        idf.setText(String.valueOf(categories.getIdforum()));
        tnameForum.setText(String.valueOf(categories.getName()));

        return convertView;

    }

}
