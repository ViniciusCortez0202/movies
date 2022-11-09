package com.stant.movies.data.repositories

import com.google.gson.JsonObject
import com.stant.movies.entities.MovieEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesRepository {
    @GET("/3/movie/now_playing")
    suspend fun get(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("page") page: Int): JsonObject

    @GET("/3/movie/{movie_id}")
    suspend fun getDetails(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String, @Query("language") language: String): JsonObject

    @GET("/3/search/movie")
    suspend fun search(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("page") page: Int, @Query("query") query: String): JsonObject
}