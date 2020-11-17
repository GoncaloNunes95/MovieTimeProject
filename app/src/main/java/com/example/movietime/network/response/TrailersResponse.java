package com.example.movietime.network.response;

public class TrailersResponse {

    private final String key, name;

    public TrailersResponse(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey(){
        return key;
    }

    public String getName(){
        return name;
    }

}
