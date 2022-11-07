package com.stant.movies.data.repositories

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresRepository {
    @GET("/3/genre/movie/list")
    suspend fun get(@Query("api_key") apiKey: String, @Query("language") language: String): JsonObject
}