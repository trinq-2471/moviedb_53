package com.sun.moviedb_53.ui.hot

import com.sun.moviedb_53.data.model.HotMovie
import com.sun.moviedb_53.utils.BasePresenter
import com.sun.moviedb_53.utils.HotMovieType

interface HotMovieContact {

    interface View {
        fun onGetMoviesSuccess(movies: MutableList<HotMovie?>)
        fun onError(exception: Exception?)
    }

    interface Presenter : BasePresenter<View> {
        fun getHotMovies(page: Int, hotMovieType: HotMovieType)
    }
}
