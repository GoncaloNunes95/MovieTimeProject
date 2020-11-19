package com.example.movietime.network.response;

public class ReviewsResponse {

    private final String content;

    public ReviewsResponse(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

