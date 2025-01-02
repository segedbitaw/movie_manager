package com.example.moviemanager.data.localdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviemanager.data.model.Movie
@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovie(movie:Movie)

    @Delete
    fun deleteMovie(vararg  items:Movie)

    @Update
    fun updateItem(item: Movie)

    @Query("SELECT * FROM movies ORDER BY movie_name ASC")
    fun getMovies() : LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE id =:id")
    fun getMovie(id:Int) : Movie

    @Query("DELETE FROM movies")
    fun deleteAll()
}