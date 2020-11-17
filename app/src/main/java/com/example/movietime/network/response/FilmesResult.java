package com.example.movietime.network.response;

import java.util.List;

public class FilmesResult {

    private final List<FilmeResponse> results;

    public FilmesResult(List<FilmeResponse> results) {
        this.results = results;
    }

    public List<FilmeResponse> getResults() {
        return results;
    }
}
