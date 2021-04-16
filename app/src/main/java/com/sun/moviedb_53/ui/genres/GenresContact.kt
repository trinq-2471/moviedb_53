package com.sun.moviedb_53.ui.genres

import com.sun.moviedb_53.data.model.Genre
import com.sun.moviedb_53.data.model.GenresMovie
import com.sun.moviedb_53.utils.BasePresenter

interface GenresContact {

    interface View {
        fun onGetGenresSuccess(listGenres: MutableList<Genre?>)
        fun onGetGenresMovieSuccess(listMovieGenres: MutableList<GenresMovie?>)
        fun onError(exception: Exception?)
    }

    interface Presenter : BasePresenter<View> {
        fun getGenres()
        fun getGenresMovie(page: Int, query: String)
    }
}
