package com.example.moviemanager.data.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviemanager.data.model.Movie

@Database(entities = arrayOf(Movie::class), version = 2, exportSchema = false)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun moviesDao() : MovieDao
    companion object {

        @Volatile
        private var instance:MovieDataBase? = null

        fun getDatabase(context: Context) = instance ?: synchronized(this) {
            Room.databaseBuilder(context.applicationContext, MovieDataBase::class.java,"movies_db")
                .allowMainThreadQueries().build()
        }
    }
}