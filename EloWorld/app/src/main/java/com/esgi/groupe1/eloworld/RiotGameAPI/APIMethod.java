package com.esgi.groupe1.eloworld.RiotGameAPI;

import android.util.Log;

import com.esgi.groupe1.eloworld.method.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;

/**
 * Created by Christopher on 10/05/2015.
 */
public class APIMethod {
    /*
    Get summoner objects mapped by standardized summoner name for a given list of summoner names
    @param String Pseudo
    @param Server
    @return Json of Summoner data
     */
    public JSONObject getInfoSummonerByPseudo(String pseudo,String server){
        String url ="https://"+server+".api.pvp.net/api/lol/"+server+"/v1.4/summoner/by-name/"+pseudo+"?api_key=08324d59-9c27-4aff-9942-2a86e04654e0";
        JSONObject json ;
        json = JSONParser.makeHttpRequestAPI(url);
        Log.d("WHY YOU DON'T WORK", String.valueOf(json));
        return json;

    }

    /*
    Get leagues mapped by summoner ID for a given list of summoner IDs
    @param int Summoner Id
    @param String Server
    @return JSON
     */
    public JSONObject getInfoSummonerById(int idSummoner,String server){
        String url ="https://"+server+".api.pvp.net/api/lol/"+server+"/v2.5/league/by-summoner/"+idSummoner+"/entry?api_key=08324d59-9c27-4aff-9942-2a86e04654e0";
        JSONObject json = JSONParser.makeHttpRequestAPI(url);
        return json;
    }
    /*
    get The summoner division
    @param int Summoner Id
    @param String Server
    @return The Summoner division in RANKED_SOLO_5x5
     */
    public  String getRankUser(int SummonerIds,String Server) throws JSONException {
        String rank;
        JSONObject Summoner =new APIMethod().getInfoSummonerById(SummonerIds, Server);
        JSONArray jsonArray = Summoner.getJSONArray(String.valueOf(SummonerIds));

        JSONObject data = jsonArray.getJSONObject(0);

        JSONArray dataDivision = data.getJSONArray("entries");
        JSONObject data2 = dataDivision.getJSONObject(0);
        String division =data2.getString("division");
        String tier = data.getString("tier");
        rank= tier+" "+division;
        Log.d("Rank",rank);
        return rank;

    }
    /*
    This object contains recent games information.
    @param int Summoner Id
    @param String Server
    @return
     */
    public static final JSONObject getGames(){
        String url = "https://euw.api.pvp.net/api/lol/euw/v1.3/game/by-summoner/57733317/recent?api_key=08324d59-9c27-4aff-9942-2a86e04654e0";
        JSONObject jsonObject = JSONParser.makeHttpRequestAPI(url);

        return jsonObject;
    }

    public static JSONObject championInfo(int idChamp){
        String url = "https://global.api.pvp.net/api/lol/static-data/euw/v1.2/champion/"+idChamp+"?api_key=08324d59-9c27-4aff-9942-2a86e04654e0";
        JSONObject object = JSONParser.makeHttpRequestAPI(url);


        return object;

    }

    public  static  JSONObject summonerSpell(int idspell){
        String url = "https://global.api.pvp.net/api/lol/static-data/euw/v1.2/summoner-spell/"+idspell+"?api_key=08324d59-9c27-4aff-9942-2a86e04654e0";
        JSONObject object = JSONParser.makeHttpRequestAPI(url);


        return object;
    }




}
