package com.example.movietime.data.mapper;

import com.example.movietime.data.Reviews;
import com.example.movietime.network.response.ReviewsResponse;

import java.util.ArrayList;
import java.util.List;

public class ReviewsMapper {

    //Obtém lista de reviews response ( List<ReviewsResponse>  ), e converte para uma lista de reviews ( List<Reviews> reviewslist ).
    public static List<Reviews> ResponseToDominio(List<ReviewsResponse> listaReviewsResponse) {

        List<Reviews> reviewsList = new ArrayList<>();

        for (ReviewsResponse reviewsResponse : listaReviewsResponse) {

            final Reviews reviews = new Reviews(reviewsResponse.getContent());
            reviewsList.add(reviews);

        }
        return reviewsList;
    }
}
