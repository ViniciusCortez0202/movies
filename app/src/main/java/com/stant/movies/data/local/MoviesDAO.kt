package com.stant.movies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stant.movies.entities.MovieEntity

@Dao
interface MoviesDAO {
    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getAll(id: Int): MovieEntity

    @Insert
    fun insertAll(vararg movies: MovieEntity)
}