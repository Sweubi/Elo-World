package com.esgi.groupe1.eloworld.Model;

/**
 * Created by Christopher on 14/07/2015.
 */
public class Message {
    private String message;
    private int idConversation;
    private int idUser;
    private String date;
    private int idMessage;
    private String summonername;

    public Message(int idMessage, String date, String message, int idConversation, int idUser, String summonername) {
        this.idMessage = idMessage;
        this.date = date;
        this.message = message;
        this.idConversation = idConversation;
        this.idUser = idUser;
    }

    public String getMessage() {
        return message;
    }

    public String getSummonername() {
        return summonername;
    }

    public void setSummonername(String summonername) {
        this.summonername = summonername;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", idConversation=" + idConversation +
                ", idUser=" + idUser +
                ", date='" + date + '\'' +
                ", idMessage=" + idMessage +
                '}';
    }
}
