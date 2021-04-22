package com.sun.moviedb_53.utils

import com.sun.moviedb_53.data.model.MovieDetail
import com.sun.moviedb_53.data.model.SearchMovie

object FakeData {
    const val ID_MOVIE_DETAIL = 399566
    const val PAGE_SEARCH = 1
    const val QUERY_SEARCH = "game"

    val SEARCH_MODEL = mutableListOf<SearchMovie?>(
        SearchMovie(
            445571, "Game Night", "path 1"
        ),
        SearchMovie(
            396371, "Molly's Game", "path 2"
        )
    )
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
