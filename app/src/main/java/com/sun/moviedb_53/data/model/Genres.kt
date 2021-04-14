package com.sun.moviedb_53.data.model

data class Genres (
    val id : Int,
    val name : String,
    val isClick : Boolean = false
)

object GenresEntry {
    const val ID = "id"
    const val NAME = "name"
    const val LIST_GENRES = "genres"
}
