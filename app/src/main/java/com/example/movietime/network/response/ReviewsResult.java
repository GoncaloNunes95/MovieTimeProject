package com.example.movietime.network.response;

import java.util.List;

public class ReviewsResult {

    private final List<ReviewsResponse> results;

    public ReviewsResult(List<ReviewsResponse> results) {
        this.results = results;
    }

    public List<ReviewsResponse> getResults() {
        return results;
    }

}
