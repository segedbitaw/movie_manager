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
    @ColumnInfo(name = "realese_year")
    val year: String,
    @ColumnInfo(name = "movie_poster")
    val photo: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}


object ItemManager {
    val items: MutableList<Movie> = mutableListOf()

    fun add(item: Movie) {
        items.add(item)
    }

    fun remove(index: Int) {
        if (index in items.indices) {
            items.removeAt(index)
        } else {
            throw IndexOutOfBoundsException("Index $index is out of bounds for the item list.")
        }
    }
    fun update(position: Int, updatedItem: Movie) {
        items[position] = updatedItem
    }
}
