package com.stant.movies.data

import android.content.Context
import androidx.room.Room
import com.stant.movies.data.local.Database

class RoomConnection : Connection<Database> {
    override fun connect(vararg info: Any?): Database {
        return Room.databaseBuilder(
            info.first() as Context,
            Database::class.java, "movies"
        ).build()
    }
}