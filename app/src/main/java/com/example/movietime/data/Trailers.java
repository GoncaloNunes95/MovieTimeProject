package com.example.movietime.data;

import java.io.Serializable;

public class Trailers implements Serializable {

    private final String key, name;

    public Trailers(String key, String name) {

        this.key = key;
        this.name = name;

    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

}
