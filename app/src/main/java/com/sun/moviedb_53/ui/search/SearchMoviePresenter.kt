package com.sun.moviedb_53.ui.search

import com.sun.moviedb_53.data.model.SearchMovie
import com.sun.moviedb_53.data.source.remote.OnFetchDataJsonListener
import com.sun.moviedb_53.data.source.repository.MovieRepository


class SearchMoviePresenter internal constructor(private val repository: MovieRepository?) :
    SearchMovieContact.Presenter {

    private var view: SearchMovieContact.View? = null

    override fun getSearchResult(page: Int, query: String) {
        repository?.onGetSearchResult(
            page,
            query,
            object : OnFetchDataJsonListener<MutableList<SearchMovie?>> {
                override fun onSuccess(data: MutableList<SearchMovie?>) {
                    view?.onSearchSuccess(data)
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                }
            }
        )
    }

    override fun onStart() {}

    override fun onStop() {
        this.view = null
    }

    override fun setView(view: SearchMovieContact.View?) {
        this.view = view
    }
}
