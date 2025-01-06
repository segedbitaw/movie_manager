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

    private val _chosenMovie = MutableLiveData<Movie>()
    val chosenMovie : LiveData<Movie> get() = _chosenMovie

    fun setMovie(movie:Movie) {
        _chosenMovie.value = movie
    }

    fun addMovie(movie: Movie) {
        repository.addMovie(movie)
    }
    fun updateMovie(movie : Movie){
        repository.updateMovie(movie) // Update in Room database
    }

    fun deleteMovie(movie:Movie) {

        repository.deleteMovie(movie) // Perform the database operation

    }
    fun deleteAll() {
        repository.deleteAll()
    }
}