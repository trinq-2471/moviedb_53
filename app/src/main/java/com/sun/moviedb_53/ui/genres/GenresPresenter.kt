package com.sun.moviedb_53.ui.genres

import com.sun.moviedb_53.data.model.Genre
import com.sun.moviedb_53.data.model.GenresMovie
import com.sun.moviedb_53.data.source.remote.OnFetchDataJsonListener
import com.sun.moviedb_53.data.source.repository.MovieRepository

class GenresPresenter internal constructor(private val repository: MovieRepository?) :
    GenresContact.Presenter {

    private var view: GenresContact.View? = null

    override fun getGenres() {
        repository?.getGenres(object : OnFetchDataJsonListener<MutableList<Genre?>> {
            override fun onSuccess(data: MutableList<Genre?>) {
                view?.onGetGenresSuccess(data)
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
            }
        })
    }

    override fun getGenresMovie(page: Int, query: String) {
        repository?.onGetGenresMovie(
            page,
            query,
            object : OnFetchDataJsonListener<MutableList<GenresMovie?>> {
                override fun onSuccess(data: MutableList<GenresMovie?>) {
                    view?.onGetGenresMovieSuccess(data)
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                }
            })
    }

    override fun onStart() {
        getGenres()
    }

    override fun onStop() {
        this.view = null
    }

    override fun setView(view: GenresContact.View?) {
        this.view = view
    }
}
