package com.sun.moviedb_53.ui.detail.movie

import com.sun.moviedb_53.data.model.*
import com.sun.moviedb_53.utils.BasePresenter

interface MovieDetailContact {

    interface View {
        fun loadContentMovieOnSuccess(movieDetail: MovieDetail)
        fun loadVideoTrailerOnSuccess(video: VideoYoutube?)
        fun loadListActorOnSuccess(movies: List<Actor>)
        fun loadRecommendationOnSuccess(movies: List<HotMovie>)
        fun onError(exception: Exception?)
    }

    interface Presenter : BasePresenter<View> {
        fun getMovieDetail(id: Int)
        fun getVideoTrailer(idMovieDetail: Int)
        fun getListMovieRecommendations(idMovieDetail: Int)
        fun getActorInMovieDetail(idMovieDetail: Int)
        fun deleteFavorite(idMovieDetail: Int): Boolean
        fun insertFavorite(favorite: Favorite): Boolean
        fun checkFavorite(idMovieDetail: Int): Boolean
    }
}
