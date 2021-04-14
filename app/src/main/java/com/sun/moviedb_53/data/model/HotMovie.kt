package com.sun.moviedb_53.data.model

data class HotMovie(
    val id: Int?,
    val title: String?,
    val posterPath: String?,
    val voteAverage: Double?
)

object HotMovieEntry {
    const val MOVIE = "results"
    const val ID = "id"
    const val VOTE = "vote_average"
    const val TITLE = "title"
    const val URL_IMAGE = "poster_path"
}
