package com.esgi.groupe1.eloworld.Objects;

/**
 * Created by Christopher on 16/07/2015.
 */
public class Friend {
    private String name;
    private int id;

    public Friend(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return  name;

    }
}
