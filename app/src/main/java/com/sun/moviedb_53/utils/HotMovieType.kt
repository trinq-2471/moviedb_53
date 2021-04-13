package com.sun.moviedb_53.utils

enum class HotMovieType(val path: String) {
    POPULAR("popular?"),
    TOP_RATED("top_rated?"),
    UP_COMING("upcoming?")
}
