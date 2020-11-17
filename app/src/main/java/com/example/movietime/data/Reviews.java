package com.example.movietime.data;

import java.io.Serializable;

public class Reviews implements Serializable {

    private final String content;

    public Reviews(String content) {

        this.content = content;

    }

    public String getContent() {
        return content;
    }

}
