package com.sun.moviedb_53.utils

import com.sun.moviedb_53.BuildConfig

object Constant {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500"
    const val BASE_LANGUAGE = "&language=en-US"
    const val BASE_PAGE = "&page="
    const val DEFAULT_PAGE = 1
    const val BASE_API_KEY = "api_key=" + BuildConfig.API_KEY
}
