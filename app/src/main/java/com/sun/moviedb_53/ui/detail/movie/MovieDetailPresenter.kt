package com.sun.moviedb_53.ui.detail.movie

import com.sun.moviedb_53.data.model.Favorite
import com.sun.moviedb_53.data.model.MovieDetail
import com.sun.moviedb_53.data.model.VideoYoutube
import com.sun.moviedb_53.data.source.repository.MovieRepository
import com.sun.moviedb_53.data.source.remote.OnFetchDataJsonListener
import com.sun.moviedb_53.data.source.repository.FavoriteRepository

class MovieDetailPresenter(
    private val repository: MovieRepository,
    private val favoriteRepository: FavoriteRepository
) :
    MovieDetailContact.Presenter {

    private var view: MovieDetailContact.View? = null

    override fun getMovieDetail(id: Int) {
        repository.getMovieDetails(id, object : OnFetchDataJsonListener<MovieDetail> {
            override fun onSuccess(data: MovieDetail) {
                if (checkFavorite(data.id)) {
                    data.isFavorite = true
                }
                view?.loadContentMovieOnSuccess(data)
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
            }
        })
    }

    override fun getVideoTrailer(idMovieDetail: Int) {
        repository.getListVideoYoutubeInMovieDetail(
            idMovieDetail,
            object : OnFetchDataJsonListener<List<VideoYoutube>> {
                override fun onSuccess(data: List<VideoYoutube>) {
                    view?.loadVideoTrailerOnSuccess(data.firstOrNull())
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                }
            })
    }

    override fun getListMovieRecommendations(idMovieDetail: Int) {}

    override fun getActorInMovieDetail(idMovieDetail: Int) {}

    override fun deleteFavorite(idMovieDetail: Int) =
        favoriteRepository.deleteFavorite(idMovieDetail)

    override fun insertFavorite(favorite: Favorite) = favoriteRepository.saveFavorite(favorite)

    override fun checkFavorite(idMovieDetail: Int) = favoriteRepository.checkFavorite(idMovieDetail)

    override fun onStart() {}

    override fun onStop() {
        view = null
    }

    override fun setView(view: MovieDetailContact.View?) {
        this.view = view
    }
}
