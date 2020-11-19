package com.example.movietime.network;

import com.example.movietime.network.response.FilmesResult;
import com.example.movietime.network.response.ReviewsResult;
import com.example.movietime.network.response.TrailersResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IFilmesService {

    @GET("movie/popular")
    Call<FilmesResult> FilmesPopulares(@Query("page") int page, @Query("api_key") String chaveAPI);

    @GET("movie/top_rated")
    Call<FilmesResult> FilmesMelhoresClassificados(@Query("page") int page, @Query("api_key") String chaveAPI);

    @GET("movie/upcoming")
    Call<FilmesResult> FilmesUpComing(@Query("page") int page, @Query("api_key") String chaveAPI);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewsResult> Reviews(@Path("movie_id") String id, @Query("api_key") String chaveAPI);

    @GET("movie/{movie_id}/videos")
    Call<TrailersResult> Trailers(@Path("movie_id") String id, @Query("api_key") String chaveAPI);

    @GET("search/movie")
    Call<FilmesResult> SearchMovies(@Query("query") String search, @Query("api_key") String key);
}
