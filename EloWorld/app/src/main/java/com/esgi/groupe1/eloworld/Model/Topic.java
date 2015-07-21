package com.esgi.groupe1.eloworld.Model;

/**
 * Created by Christopher on 21/06/2015.
 */
public class Topic {
    private String Sujet;
    private String date;
    private String Auteur;
    private String idtopic;

    public Topic( String sujet,String auteur, String date,String idtopic) {
        super();
        Auteur = auteur;
        Sujet = sujet;
        this.idtopic =idtopic;
        this.date = date;

    }

    public String getSujet() {
        return Sujet;
    }

    public void setSujet(String sujet) {
        Sujet = sujet;
    }

    public String getAuteur() {
        return Auteur;
    }

    public void setAuteur(String auteur) {
        Auteur = auteur;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getIdtopic() {
        return idtopic;
    }
}
