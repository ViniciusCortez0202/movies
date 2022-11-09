package com.stant.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stant.movies.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun moviesDao(): MoviesDAO
}