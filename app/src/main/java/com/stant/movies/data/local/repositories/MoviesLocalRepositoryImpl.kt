package com.stant.movies.data.local.repositories

import android.content.Context
import com.stant.movies.data.Connection
import com.stant.movies.data.local.Database
import com.stant.movies.entities.MovieEntity

class MoviesLocalRepositoryImpl (connection: Connection<Database>, context: Context) {

    private var connection: Connection<Database>
    private var context: Context


    init {
        this.connection = connection
        this.context  = context
    }

    suspend fun get(id: Int): MovieEntity {
        return connection.connect(context).moviesDao().getAll(id)
    }

    fun insert(movie: MovieEntity){
        connection.connect(context).moviesDao().insertAll(movie)
    }

}