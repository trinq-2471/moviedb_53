package com.sun.moviedb_53.utils

enum class DetailMovieType(val path: String) {
    MOVIE_DETAIL("?"),
    VIDEO_YOUTUBE("/videos"),
    ACTOR("/credits"),
    RECOMMENDATIONS("/recommendations")
}
