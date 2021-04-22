package com.sun.moviedb_53.utils

import com.sun.moviedb_53.data.model.MovieDetail

object FakeData {
    const val ID_MOVIE_DETAIL = 399566

    val MOVIE_DETAIL =
        MovieDetail(
            0, "title",
            "photoUrl",
            0.0,
            "description",
            "photoPoster",
            mutableListOf(),
            "releaseDate",
            "tagLine"
        )
}
