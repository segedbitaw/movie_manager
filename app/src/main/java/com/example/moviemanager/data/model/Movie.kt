package com.example.moviemanager.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movies")
data class Movie(

    @ColumnInfo(name = "movie_name")
    val title: String,
    @ColumnInfo(name = "movie_desc")
    val description: String,
    @ColumnInfo(name = "movie_genre")
    val genre: String,
    @ColumnInfo(name = "realese_year")
    val year: String,
    @ColumnInfo(name = "rating")
    val stars: Float,
    @ColumnInfo(name = "movie_poster")
    val photo: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}

