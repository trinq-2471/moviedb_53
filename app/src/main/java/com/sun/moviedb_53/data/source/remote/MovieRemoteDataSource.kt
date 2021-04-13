package com.sun.moviedb_53.data.source.remote

import com.sun.moviedb_53.data.model.HotMovie
import com.sun.moviedb_53.data.source.MovieDataSource
import com.sun.moviedb_53.data.source.remote.fetchjson.GetJsonFromUrl
import com.sun.moviedb_53.utils.Constant
import com.sun.moviedb_53.utils.DetailMovieType
import com.sun.moviedb_53.utils.HotMovieType
import com.sun.moviedb_53.utils.KeyEntityType

@Suppress("DEPRECATION")
class MovieRemoteDataSource : MovieDataSource.Remote {

    private val endPointParams = Constant.BASE_API_KEY + Constant.BASE_LANGUAGE

    override fun getHotMovies(
        page: Int,
        hotMovieType: HotMovieType,
        listener: OnFetchDataJsonListener<MutableList<HotMovie?>>
    ) {
        val baseUrl = Constant.BASE_URL + MOVIE_TYPE + hotMovieType.path +
                Constant.BASE_API_KEY +
                Constant.BASE_LANGUAGE +
                Constant.BASE_PAGE + page
        GetJsonFromUrl(listener, KeyEntityType.MOVIE_ITEM).execute(baseUrl)
    }

    override fun <T> getDataInMovieDetail(
        idMovie: Int,
        detailMovieType: DetailMovieType,
        listener: OnFetchDataJsonListener<T>
    ) {
        val stringUrl = Constant.BASE_URL +
                MOVIE_TYPE + idMovie +
                detailMovieType.path +
                endPointParams
        GetJsonFromUrl(listener, KeyEntityType.MOVIE_DETAIL).execute(stringUrl)
    }

    companion object {
        private const val MOVIE_TYPE = "movie/"
        private var instance: MovieRemoteDataSource? = null

        fun getInstance() = instance ?: MovieRemoteDataSource().also { instance = it }
    }
}
