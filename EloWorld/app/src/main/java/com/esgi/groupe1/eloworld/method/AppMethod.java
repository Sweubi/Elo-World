package com.esgi.groupe1.eloworld.method;




import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Christopher on 28/04/2015.
 */
public class AppMethod {

    //this method check if the password and confirm password are same.
    public boolean checkequalityPassword(String valuepwd,String valueconfirmpwd){
        if (valuepwd.equals(valueconfirmpwd)){
            return true;
        }
        else {
            return false;
        }
    }
    public String PseudoAuthor(String id){
        String pseudo ;
        String url = "http://manouanachristopeher.site90.net/EloWorldWeb/Code/WebService/divers/getPseudo.php";
        List list = new ArrayList();
        list.add(new BasicNameValuePair("idUser",id));
        JSONObject objectPObject = JSONParser.makeHttpRequest(url,list);
        pseudo = objectPObject.optString("pseudo");
        return pseudo;
    }

    public String ServerRiot (String server){
        String rioServer = null;
        switch (server){
            case "Brazil":
                rioServer ="Br";
                break;
            case "EU Nordic and East":
                rioServer ="eune";
                break;
            case "EU West":
               rioServer = "euw";
                break;
            case "Latin America North":
               rioServer = "lan";
                break;
            case "Latin America South":
               rioServer = "las";
                break;
            case "North America":
                rioServer = "na";
                break;
            case "Oceania":
               rioServer = "oce";
                break;
            case "Russia":
                rioServer="ru";
                break;
            case "Turkey":
                rioServer = "tr";
                break;
            case "Korea":
                rioServer ="kr";
                break;
        }
        return rioServer;
    }

    public  String urlItem(int idItem){
        String url = "http://ddragon.leagueoflegends.com/cdn/5.2.1/img/item/"+idItem+".png";
        return url;
    }

    public  String urlSpell(String key){
        String url = "http://ddragon.leagueoflegends.com/cdn/5.2.1/img/spell/"+key+".png";
        return url;
    }
   /* private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }*/


}
