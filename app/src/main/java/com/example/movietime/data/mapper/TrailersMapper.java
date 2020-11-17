package com.example.movietime.data.mapper;

import com.example.movietime.data.Trailers;
import com.example.movietime.network.response.TrailersResponse;

import java.util.ArrayList;
import java.util.List;

public class TrailersMapper {

    public static List<Trailers> ResponseToDominio(List<TrailersResponse> listaTrailersResponse) {

        List<Trailers> trailersList = new ArrayList<>();

        for (TrailersResponse trailersResponse : listaTrailersResponse) {

            final Trailers trailers = new Trailers(trailersResponse.getKey(), trailersResponse.getName());
            trailersList.add(trailers);

        }
        return trailersList;
    }
}
