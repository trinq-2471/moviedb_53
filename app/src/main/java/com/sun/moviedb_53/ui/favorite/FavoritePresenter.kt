package com.sun.moviedb_53.ui.favorite

import com.sun.moviedb_53.data.source.repository.FavoriteRepository

class FavoritePresenter(
    private val favoriteRepository: FavoriteRepository
) : FavoriteContact.Presenter {

    private var view: FavoriteContact.View? = null

    override fun getFavorites() = favoriteRepository.getListFavorite()

    override fun deleteFavorite(idMovieDetail: Int) =
        favoriteRepository.deleteFavorite(idMovieDetail)

    override fun onStart() {
        view?.loadFavoriteOnSuccess(getFavorites())
    }

    override fun onStop() {
        view = null
    }

    override fun setView(view: FavoriteContact.View?) {
        this.view = view
    }
}
