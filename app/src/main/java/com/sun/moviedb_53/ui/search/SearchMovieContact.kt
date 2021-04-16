package com.sun.moviedb_53.ui.search

import com.sun.moviedb_53.data.model.SearchMovie
import com.sun.moviedb_53.utils.BasePresenter

interface SearchMovieContact {

    interface View {
        fun onSearchSuccess(listSearchMovie: MutableList<SearchMovie?>)
        fun onError(exception: Exception?)
    }

    interface Presenter : BasePresenter<View> {
        fun getSearchResult(page: Int, query: String)
    }
}
