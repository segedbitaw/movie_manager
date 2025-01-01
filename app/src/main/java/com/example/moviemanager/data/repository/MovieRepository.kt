package com.example.moviemanager.data.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.moviemanager.data.localdb.MovieDao
import com.example.moviemanager.data.localdb.MovieDataBase
import com.example.moviemanager.data.model.Movie

class MovieRepository(application: Application): AndroidViewModel(application) {

    private var movieDao:MovieDao?

    init {
        val db  = MovieDataBase.getDatabase(application.applicationContext)
        movieDao = db?.moviesDao()
    }

    fun getMovies() = movieDao?.getMovies()

    fun addMovie(movie:Movie) {
        movieDao?.addMovie(movie)
    }

    fun deleteMovie(movie: Movie) {
        movieDao?.deleteMovie(movie)
    }

    fun getMovie(id:Int)  = movieDao?.getMovie(id)

    fun deleteAll() {
        movieDao?.deleteAll()
    }
}