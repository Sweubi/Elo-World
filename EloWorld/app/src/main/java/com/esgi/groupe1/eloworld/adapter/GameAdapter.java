package com.esgi.groupe1.eloworld.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.groupe1.eloworld.appObject.Games;
import com.esgi.groupe1.eloworld.R;
import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by Christopher on 03/06/2015.
 */
public class GameAdapter extends ArrayAdapter {
    private Context context;
    private Activity activity;
    private List<Games> games;
    TextView kills,deaths,assists;
    ImageView square,squareSpell1,squareSpell2,i0,i1,i2,i3,i4,i5;

    public GameAdapter(Context context, List<Games> games) {
        super(context, R.layout.game_list,games);
        this.games = games;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Games mesgames = games.get(position);
        boolean bwin = mesgames.isResGame();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (bwin == true){
            convertView = inflater.inflate(R.layout.game_list,parent,false);
        }else {
            convertView = inflater.inflate(R.layout.game_list_lose,parent,false);
        }
        String champion =mesgames.getChampion();
        String spell1 = mesgames.getSpell1();
        String spell2 = mesgames.getSpell2();
        int item0 = mesgames.getIdItem0();
        int item1 = mesgames.getIdItem1();
        int item2 = mesgames.getIdItem2();
        int item3= mesgames.getIdItem3();
        int item4= mesgames.getIdItem4();
        int item5 = mesgames.getIdItem5();

        square = (ImageView) convertView.findViewById(R.id.square);
        Picasso.with(getContext()).load(urlChampionSquare(champion)).into(square);

        squareSpell1 = (ImageView) convertView.findViewById(R.id.spell1);
        Picasso.with(getContext()).load(urlSummonerSpell(spell1)).into(squareSpell1);
        squareSpell2 = (ImageView) convertView.findViewById(R.id.spell2);
        Picasso.with(getContext()).load(urlSummonerSpell(spell2)).into(squareSpell2);
        i0 = (ImageView) convertView.findViewById(R.id.item0);
        Picasso.with(getContext()).load(urlStuff(item0)).into(i0);
        i1 = (ImageView) convertView.findViewById(R.id.item1);
        Picasso.with(getContext()).load(urlStuff(item1)).into(i1);
        i2 = (ImageView) convertView.findViewById(R.id.item2);
        Picasso.with(getContext()).load(urlStuff(item2)).into(i2);
        i3 = (ImageView) convertView.findViewById(R.id.item3);
        Picasso.with(getContext()).load(urlStuff(item3)).into(i3);
        i4 = (ImageView) convertView.findViewById(R.id.item4);
        Picasso.with(getContext()).load(urlStuff(item4)).into(i4);
        i5 = (ImageView) convertView.findViewById(R.id.item5);
        Picasso.with(getContext()).load(urlStuff(item5)).into(i5);

        kills = (TextView) convertView.findViewById(R.id.tkills);
        deaths= (TextView) convertView.findViewById(R.id.tdeaths);
        assists= (TextView) convertView.findViewById(R.id.tassists);

        //Log.d("mes games", String.valueOf(champion));
        kills.setText(String.valueOf(mesgames.getKills()));
        deaths.setText(String.valueOf(mesgames.getDeaths()));
        assists.setText(String.valueOf(mesgames.getAssits()));

        return convertView;
    }
    public String urlChampionSquare(String champion){
       String url = "http://ddragon.leagueoflegends.com/cdn/5.2.1/img/champion/"+champion+".png";
        return url;
    }
    public String urlSummonerSpell(String spell){
        String url ="http://ddragon.leagueoflegends.com/cdn/5.2.1/img/spell/"+spell+".png";
        return url;

    }
    public String urlStuff(int item){
        String url;
        if (item != 0) {
             url = "http://ddragon.leagueoflegends.com/cdn/5.2.1/img/item/"+item+".png";
        }else {
             url = "http://manouanachristopeher.site90.net/EloWorldWeb/img/empty.png";

        }
        return url;
    }


}
