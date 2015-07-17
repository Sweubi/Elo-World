package com.esgi.groupe1.eloworld.RiotGameAPI;

import android.util.Log;

import com.esgi.groupe1.eloworld.appObject.Games;
import com.esgi.groupe1.eloworld.method.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    public static final JSONObject getGames(int idSummoner, String server){
        String url = "https://"+server+".api.pvp.net/api/lol/"+server+"/v1.3/game/by-summoner/"+idSummoner+"/recent?api_key=08324d59-9c27-4aff-9942-2a86e04654e0";
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
    public static List<Games> SummonerGame(int IdSummoner, String server){
        APIMethod apiMethod =new APIMethod();
        JSONObject object = apiMethod.getGames(IdSummoner,server);

        List<Games> mesgames = new ArrayList<Games>();
        try {
            JSONArray gamesArray = object.getJSONArray("games");
            int lenght = gamesArray.length();//10
            for (int i=0; i<lenght;i++){
                JSONObject getall = gamesArray.getJSONObject(i);
                int idchamp = getall.getInt("championId");
                int idSpell1 = getall.getInt("spell1");
                int idSpell2 = getall.getInt("spell2");
                JSONObject stats = getall.getJSONObject("stats");
                int championsKilled = stats.optInt("championsKilled");
                int championsAssists = stats.getInt("assists");
                boolean wingame = stats.getBoolean("win");
                int numDeaths = stats.optInt("numDeaths");
                int idItem0 = stats.optInt("item0");
                int idItem1 = stats.optInt("item1");
                int idItem2 = stats.optInt("item2");
                int idItem3 = stats.optInt("item3");
                int idItem4 = stats.optInt("item4");
                int idItem5 = stats.optInt("item5");
                int idItem6 = stats.optInt("item6");
                JSONObject objectDataChamp = apiMethod.championInfo(idchamp);
                String champion = objectDataChamp.optString("name");
                JSONObject object1Spell = apiMethod.summonerSpell(idSpell1);
                JSONObject object1Spel2 = apiMethod.summonerSpell(idSpell2);
                String spell1 = object1Spell.getString("key");
                String spell2 = object1Spel2.getString("key");
                mesgames.add(new Games(champion, spell1, spell2, championsKilled, championsAssists, numDeaths, wingame,idItem0, idItem1, idItem2,idItem3, idItem4, idItem5,idItem6));
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return mesgames;

    }




}
