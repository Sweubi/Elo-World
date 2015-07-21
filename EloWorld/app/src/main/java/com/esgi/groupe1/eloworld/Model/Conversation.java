package com.esgi.groupe1.eloworld.Model;

/**
 * Created by Christopher on 14/07/2015.
 */
public class Conversation {
    private String nameuser;

    public Conversation(String nameuser) {
        this.nameuser = nameuser;
    }

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "nameuser='" + nameuser + '\'' +
                '}';
    }
}
