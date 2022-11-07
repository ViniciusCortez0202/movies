package com.stant.movies.model

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.stant.movies.entities.MovieEntity

class MovieModel{

    fun jsonFromList(json: JsonObject) : List<MovieEntity> {
        val gson = Gson()
        val movies: MutableList<MovieEntity> = mutableListOf()
        json.get("results")?.asJsonArray?.iterator()?.forEach {
            val movie = gson.fromJson(it, MovieEntity::class.java)
            movies.add(movie)
            }
        return movies
    }

}
