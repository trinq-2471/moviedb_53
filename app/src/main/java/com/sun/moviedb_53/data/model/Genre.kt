package com.sun.moviedb_53.data.model

data class Genre(
    val id: Int,
    val name: String,
    var isSelected : Boolean = false,
    var positionSelected : Int? = null
)

object GenresEntry {
    const val ID = "id"
    const val NAME = "name"
    const val LIST_GENRES = "genres"
}
