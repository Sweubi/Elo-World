package com.esgi.groupe1.eloworld.appObject;

/**
 * Created by Christopher on 02/06/2015.
 */
public class Games {
    private String champion,spell1,spell2;
    private int kills,assits,deaths, idItem0,idItem1,idItem2,idItem3,idItem4,idItem5,idItem6;
    private boolean resGame; //true for win or false
    public Games(String champion, String spell1, String spell2, int kills, int assits, int deaths, boolean resGame, int idItem0, int idItem1, int idItem2, int idItem3, int idItem4, int idItem5, int idItem6){
        this.champion = champion;
        this.spell1 = spell1;
        this.spell2 = spell2;
        this.kills = kills;
        this.deaths = deaths;
        this.resGame = resGame;
        this.assits = assits;
        this.idItem0 = idItem0;
        this.idItem1 = idItem1;
        this.idItem2 = idItem2;
        this.idItem3 = idItem3;
        this.idItem4 = idItem4;
        this.idItem5 = idItem5;
        this.idItem6 = idItem6;
    }

    public int getIdItem6() {
        return idItem6;
    }

    public void setIdItem6(int idItem6) {
        this.idItem6 = idItem6;
    }

    public int getIdItem0() {
        return idItem0;
    }

    public void setIdItem0(int idItem0) {
        this.idItem0 = idItem0;
    }

    public int getIdItem1() {
        return idItem1;
    }

    public void setIdItem1(int idItem1) {
        this.idItem1 = idItem1;
    }

    public int getIdItem2() {
        return idItem2;
    }

    public void setIdItem2(int idItem2) {
        this.idItem2 = idItem2;
    }

    public int getIdItem3() {
        return idItem3;
    }

    public void setIdItem3(int idItem3) {
        this.idItem3 = idItem3;
    }

    public int getIdItem4() {
        return idItem4;
    }

    public void setIdItem4(int idItem4) {
        this.idItem4 = idItem4;
    }

    public int getIdItem5() {
        return idItem5;
    }

    public void setIdItem5(int idItem5) {
        this.idItem5 = idItem5;
    }

    public String getChampion() {
        return champion;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }

    public String getSpell1() {
        return spell1;
    }

    public void setSpell1(String spell1) {
        this.spell1 = spell1;
    }

    public String getSpell2() {
        return spell2;
    }

    public void setSpell2(String spell2) {
        this.spell2 = spell2;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getAssits() {
        return assits;
    }

    public void setAssits(int assits) {
        this.assits = assits;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public boolean isResGame() {
        return resGame;
    }

    public void setResGame(boolean resGame) {
        this.resGame = resGame;
    }
}
