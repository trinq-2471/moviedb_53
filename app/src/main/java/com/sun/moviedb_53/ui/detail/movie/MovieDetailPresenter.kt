package com.sun.moviedb_53.ui.detail.movie

import com.sun.moviedb_53.data.model.Favorite
import com.sun.moviedb_53.data.model.MovieDetail
import com.sun.moviedb_53.data.source.MovieRepository
import com.sun.moviedb_53.data.source.remote.OnFetchDataJsonListener

class MovieDetailPresenter(private val repository: MovieRepository) :
    MovieDetailContact.Presenter {

    private var view: MovieDetailContact.View? = null

    override fun getMovieDetail(id: Int) {
        repository.getMovieDetails(id, object : OnFetchDataJsonListener<MovieDetail> {
            override fun onSuccess(data: MovieDetail) {
                view?.loadContentMovieOnSuccess(data)
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
            }
        })
    }

    override fun getVideoTrailer(idMovieDetail: Int) {}

    override fun getListMovieRecommendations(idMovieDetail: Int) {}

    override fun getActorInMovieDetail(idMovieDetail: Int) {}

    override fun deleteFavorite(id: Int) {}

    override fun insertFavorite(favorite: Favorite) {}

    override fun onStart() {}

    override fun onStop() {
        view = null
    }

    override fun setView(view: MovieDetailContact.View?) {
        this.view = view
    }
}
