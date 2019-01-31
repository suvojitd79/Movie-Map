package com.appcia.moviemap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("3/movie/top_rated")
    Call<MovieInfo> getTopRatedMovies(@Query("api_key") String key);

    @GET("3/movie/popular")
    Call<MovieInfo> getPopularMovies(@Query("api_key") String key);

    @GET("3/genre/movie/list")
    Call<Genre> getGenre(@Query("api_key") String key);

}
