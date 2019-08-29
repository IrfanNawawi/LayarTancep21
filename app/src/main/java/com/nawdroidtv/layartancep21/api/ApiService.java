package com.nawdroidtv.layartancep21.api;

import com.nawdroidtv.layartancep21.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovie(@Query("api_key") String apikey);

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovie(@Query("api_key") String apikey);

    @GET("search/movie")
    Call<MovieResponse> getSearchMovie(@Query("api_key") String apikey, @Query("language") String language, @Query("query") String key);
}
