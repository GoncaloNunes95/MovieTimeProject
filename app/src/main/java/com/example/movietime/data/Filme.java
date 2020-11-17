package com.example.movietime.data;

import java.io.Serializable;

public class Filme implements Serializable {

    private final String poster_path, original_title, release_date, overview;
    private final Float vote_average;
    private final int id;

    public Filme(int id, String poster_path, String original_title, String release_date, Float vote_average, String overview) {

        this.id = id;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.overview = overview;

    }

    public int getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public Float getVote_average() {
        return vote_average;
    }

    public String getSinopse() {
        return overview;
    }
}
