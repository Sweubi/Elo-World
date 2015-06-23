package com.esgi.groupe1.eloworld.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.esgi.groupe1.eloworld.Games;
import com.esgi.groupe1.eloworld.R;
import com.esgi.groupe1.eloworld.method.BitmapLruCache;


import java.util.List;

/**
 * Created by Christopher on 03/06/2015.
 */
public class GameAdapter extends ArrayAdapter {
    private Context context;
    private Activity activity;
    private List<Games> games;
    TextView kills,deaths,assists;
    NetworkImageView square,squareSpell1,squareSpell2,i0,i1,i2,i3,i4,i5;
    String urlsquare,urlSpell1,urlSpell2,urlItem0 ;



    public GameAdapter(Context context, List<Games> games) {
        super(context, R.layout.game_list,games);
        this.games = games;

    }

   /* @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int position) {
        return games.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.game_list,parent,false);

        square = (NetworkImageView) convertView.findViewById(R.id.square);
        squareSpell1 = (NetworkImageView) convertView.findViewById(R.id.spell1);
        squareSpell2 = (NetworkImageView) convertView.findViewById(R.id.spell2);
        i0 = (NetworkImageView) convertView.findViewById(R.id.item0);
        i1 = (NetworkImageView) convertView.findViewById(R.id.item1);
        i2 = (NetworkImageView) convertView.findViewById(R.id.item2);
        i3 = (NetworkImageView) convertView.findViewById(R.id.item3);
        i4 = (NetworkImageView) convertView.findViewById(R.id.item4);
        i5 = (NetworkImageView) convertView.findViewById(R.id.item5);

        kills = (TextView) convertView.findViewById(R.id.tkills);
        deaths= (TextView) convertView.findViewById(R.id.tdeaths);
        assists= (TextView) convertView.findViewById(R.id.tassists);
        Games mesgames = games.get(position);
        String champion =mesgames.getChampion();
        String spell1 = mesgames.getSpell1();
        String spell2 = mesgames.getSpell2();
        int item0 = mesgames.getIdItem0();
        int item1 = mesgames.getIdItem1();
        int item2 = mesgames.getIdItem2();
        int item3= mesgames.getIdItem3();
        int item4= mesgames.getIdItem4();
        int item5 = mesgames.getIdItem5();




        ImageLoader.ImageCache imageCache = new BitmapLruCache();
        ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(getContext()), imageCache);
        square.setImageUrl(urlChampionSquare(champion),imageLoader);
        squareSpell1.setImageUrl(urlSummonerSpell(spell1),imageLoader);
        squareSpell2.setImageUrl(urlSummonerSpell(spell2),imageLoader);
        i0.setImageUrl(urlStuff(item0),imageLoader);
        i1.setImageUrl(urlStuff(item1),imageLoader);
        i2.setImageUrl(urlStuff(item2),imageLoader);
        i3.setImageUrl(urlStuff(item3),imageLoader);
        i4.setImageUrl(urlStuff(item4),imageLoader);
        i5.setImageUrl(urlStuff(item5),imageLoader);

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
