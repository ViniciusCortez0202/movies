package com.stant.movies.model

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.stant.movies.entities.GenresEntity

class GenresModel {

    fun jsonFromList(json: JsonObject) : List<GenresEntity> {
        val gson = Gson()
        val genres: MutableList<GenresEntity> = mutableListOf()
        json.get("genres")?.asJsonArray?.iterator()?.forEach {
            val genre = gson.fromJson(it, GenresEntity::class.java)
            genres.add(genre)
        }
        return genres
    }
}