package com.sun.moviedb_53.data.model

class SearchMovie (
    val id: Int?,
    val title: String?,
    val posterPath: String?,
)

object SearchMovieEnrtry{
    const val MOVIE = "results"
    const val ID = "id"
    const val TITLE = "title"
    const val URL_IMAGE = "poster_path"
}
