package com.sun.moviedb_53.data.source.remote

import com.sun.moviedb_53.data.model.HotMovie
import com.sun.moviedb_53.data.source.MovieDataSource
import com.sun.moviedb_53.data.source.remote.fetchjson.GetJsonFromUrl
import com.sun.moviedb_53.utils.Constant
import com.sun.moviedb_53.utils.HotMovieType
import com.sun.moviedb_53.utils.KeyEntityTpye

@Suppress("DEPRECATION")
class MovieRemoteDataSource : MovieDataSource.Remote {

    override fun getHotMovies(page: Int, hotMovieType: HotMovieType, listener: OnFetchDataJsonListener<MutableList<HotMovie?>>) {
        val baseUrl = Constant.BASE_URL + MOVIE_TYPE + hotMovieType.path +
                Constant.BASE_API_KEY +
                Constant.BASE_LANGUAGE +
                Constant.BASE_PAGE + page
        GetJsonFromUrl(listener, KeyEntityTpye.MOVIE_ITEM).execute(baseUrl)
    }

    companion object {

        private const val MOVIE_TYPE = "movie/"

        private var instance: MovieRemoteDataSource? = null

        fun getInstance() = instance ?: MovieRemoteDataSource().also { instance = it }
    }
}
