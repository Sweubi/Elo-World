package com.esgi.groupe1.eloworld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


/**
 * Created by Christopher on 14/07/2015.
 */
public class MessengerAdapter extends ArrayAdapter {
    public MessengerAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        return super.getView(position, convertView, parent);
    }
}
