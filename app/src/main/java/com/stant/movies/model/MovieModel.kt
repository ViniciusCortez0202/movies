package com.stant.movies.model

import com.google.gson.Gson
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.stant.movies.entities.MovieEntity
import java.lang.Exception

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

    fun jsonFromDetails(json: JsonObject) : MovieEntity {
        val gson = Gson()
        var movie:MovieEntity = gson.fromJson(json, MovieEntity::class.java)

            var listGenres = mutableListOf<String>()
            json.get("genres")?.asJsonArray?.iterator()?.forEach {
                listGenres.add(it.asJsonObject.get("name").asString)
            }
            movie.genresNames = listGenres.joinToString{" ,"; it}
            var path = json.get("production_companies").asJsonArray?.get(0)?.asJsonObject?.get("logo_path")
            movie.logoCompany = if(path is JsonNull)  "" else path?.asString!!

            var name = json.get("production_companies")?.asJsonArray?.get(0)?.asJsonObject?.get("name")
            movie.nameCompany = if(name is JsonNull)  "" else name?.asString!!

            return movie
    }

}
