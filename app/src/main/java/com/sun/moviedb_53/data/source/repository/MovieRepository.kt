package com.sun.moviedb_53.data.source.repository

import com.sun.moviedb_53.data.model.HotMovie
import com.sun.moviedb_53.data.model.MovieDetail
import com.sun.moviedb_53.data.source.MovieDataSource
import com.sun.moviedb_53.data.source.remote.OnFetchDataJsonListener
import com.sun.moviedb_53.utils.DetailMovieType
import com.sun.moviedb_53.utils.HotMovieType

class MovieRepository private constructor(
    private val remote: MovieDataSource.Remote
) {

    fun getMovie(
        page: Int,
        hotMovieType: HotMovieType,
        listener: OnFetchDataJsonListener<MutableList<HotMovie?>>
    ) {
        remote.getHotMovies(page, hotMovieType, listener)
    }

    fun getMovieDetails(id: Int, listener: OnFetchDataJsonListener<MovieDetail>) {
        remote.getDataInMovieDetail(id, DetailMovieType.MOVIE_DETAIL, listener)
    }

    companion object {
        private var instance: MovieRepository? = null

        fun getInstance(
            remote: MovieDataSource.Remote
        ): MovieRepository {
            return instance ?: synchronized(this) {
                instance ?: MovieRepository(remote).also { instance = it }
            }
        }
    }
}
