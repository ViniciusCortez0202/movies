package com.stant.movies.data.repositories

import com.stant.movies.data.Connection
import com.stant.movies.entities.GenresEntity
import com.stant.movies.model.GenresModel
import retrofit2.Retrofit
import java.lang.Exception

class GenresRepositoryImpl (
    private val connection: Connection<Retrofit>,
    private val genreModel: GenresModel
) {

    suspend fun getlist(language: String): List<GenresEntity> {
        try {
            val connection = connection.connect()
            val repository = connection.create(GenresRepository::class.java)
            val json = repository.get(
                "f321a808e68611f41312aa8408531476",
                language
            )

            return genreModel.jsonFromList(json)
        } catch (e: Exception) {
            throw e
        }
    }
}