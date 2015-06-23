package com.esgi.groupe1.eloworld;

import java.util.Date;

/**
 * Created by Christopher on 21/06/2015.
 */
public class Topic {
    private String Sujet;
    private String date;
    private String Auteur;

    public Topic( String sujet,String auteur, String date) {
        super();
        Auteur = auteur;
        Sujet = sujet;
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
}
