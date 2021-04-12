package com.sun.moviedb_53.data.source

import com.sun.moviedb_53.data.model.HotMovie
import com.sun.moviedb_53.data.source.remote.OnFetchDataJsonListener
import com.sun.moviedb_53.utils.HotMovieType

class MovieRepository private constructor(
        private val remote: MovieDataSource.Remote,
        private val local: MovieDataSource.Local) {

    fun getMovie(page: Int,
                 hotMovieType: HotMovieType,
                 listener: OnFetchDataJsonListener<MutableList<HotMovie?>>
    ) {
        remote.getHotMovies(page, hotMovieType, listener)
    }

    companion object {

        private var INSTANCE: MovieRepository? = null

        fun getInstance(
                remote: MovieDataSource.Remote,
                local: MovieDataSource.Local
        ): MovieRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: MovieRepository(remote, local).also { INSTANCE = it }
            }
        }
    }
}
