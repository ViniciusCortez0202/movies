package com.stant.movies.data.repositories

import com.google.gson.Gson
import com.stant.movies.data.Connection
import com.stant.movies.entities.MovieEntity
import com.stant.movies.model.MovieModel
import retrofit2.Retrofit
import java.lang.Exception

class MoviesRepositoryImpl(
    private val connection: Connection<Retrofit>,
    private val movieModel: MovieModel
) {

     suspend fun getlist(language: String, page: Int): List<MovieEntity>{
        try {
            val connection = connection.connect()
            val repository = connection.create(MoviesRepository::class.java)
            val json = repository.get(
                "f321a808e68611f41312aa8408531476",
                language,
                page
            )

            return movieModel.jsonFromList(json)
        } catch (e: Exception){
            throw e
        }
    }

    suspend fun getSearch(query: String, language: String, page: Int): List<MovieEntity>{
        try {
            val connection = connection.connect()
            val repository = connection.create(MoviesRepository::class.java)
            val json = repository.search(
                "f321a808e68611f41312aa8408531476",
                language,
                page,
                query
            )

            return movieModel.jsonFromList(json)
        } catch (e: Exception){
            throw e
        }
    }

    suspend fun getDetails(idMovie: Int, language: String): MovieEntity{
        try {
            val connection = connection.connect()
            val repository = connection.create(MoviesRepository::class.java)
            var json = repository.getDetails(
                idMovie,
                "f321a808e68611f41312aa8408531476",
                language,
            )

            return movieModel.jsonFromDetails(json)
        } catch (e: Exception){
            throw e
        }
    }

}