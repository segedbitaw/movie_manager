package com.example.moviemanager.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviemanager.data.model.Movie
import com.example.moviemanager.data.repository.MovieRepository

class MovieViewModel(application: Application)  : AndroidViewModel(application)
{
    private val repository = MovieRepository(application)

    val movies : LiveData<List<Movie>>? = repository.getMovies()

    private val _chosenMovie = MutableLiveData<Movie?>()
    val chosenMovie : MutableLiveData<Movie?> get() = _chosenMovie

    fun setMovie(movie:Movie) {
        _chosenMovie.value = movie
    }
    fun clearChosenMovie() {
        _chosenMovie.value = null // This should trigger the observer
    }
    fun addMovie(movie: Movie) {
        repository.addMovie(movie)
    }
    fun updateMovie(updatedMovie: Movie) {
        repository.updateMovie(updatedMovie)
    }
    fun deleteMovie(movie:Movie) {
        repository.deleteMovie(movie)
    }
    fun deleteAll() {
        repository.deleteAll()
    }
}