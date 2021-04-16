package com.sun.moviedb_53.data.model

data class GenresMovie(
    val id: Int?,
    val title: String?,
    val posterPath: String?,
)

object GenresMovieEntry{
    const val MOVIE = "results"
    const val ID = "id"
    const val TITLE = "title"
    const val URL_IMAGE = "poster_path"
}
