package com.esgi.groupe1.eloworld;

/**
 * Created by Christopher on 21/06/2015.
 */
public class ForumObjet {
    private int idforum;
    private String name;

    public ForumObjet(String name, int idforum) {
        this.name = name;
        this.idforum = idforum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForumObjet that = (ForumObjet) o;

        if (idforum != that.idforum) return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = idforum;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public int getIdforum() {
        return idforum;
    }

    public void setIdforum(int idforum) {
        this.idforum = idforum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
