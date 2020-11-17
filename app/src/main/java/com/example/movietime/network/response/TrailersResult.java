package com.example.movietime.network.response;

import java.util.List;

public class TrailersResult {

    private final List<TrailersResponse> results;

    public TrailersResult(List<TrailersResponse> results) {
        this.results = results;
    }

    public List<TrailersResponse> getResults() {
        return results;
    }

}
