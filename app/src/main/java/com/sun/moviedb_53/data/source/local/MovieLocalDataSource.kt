package com.sun.moviedb_53.data.source.local

import com.sun.moviedb_53.data.source.MovieDataSource

class MovieLocalDataSource : MovieDataSource.Local {

    companion object {
        private var instance: MovieLocalDataSource? = null
        fun getInstance() = instance ?: MovieLocalDataSource().also { instance = it }
    }
}
