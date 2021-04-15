package com.sun.moviedb_53.data.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val photoUrl: String,
    val rate: Double,
    val description: String,
    val photoPoster: String,
    val genres: List<Genre>,
    val releaseDate: String,
    val tagLine: String,
    var isFavorite: Boolean = false
)

object MovieDetailEntry {
    const val ID = "id"
    const val TITLE = "title"
    const val PHOTO_URL = "backdrop_path"
    const val LIST_GENRES = "genres"
    const val RATE = "vote_average"
    const val DESCRIPTION = "overview"
    const val PHOTO_POSTER = "poster_path"
    const val RELEASE_DATE = "release_date"
    const val TAG_LINE = "tagline"
}
