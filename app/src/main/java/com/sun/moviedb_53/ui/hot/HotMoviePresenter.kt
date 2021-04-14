package com.sun.moviedb_53.ui.hot

import com.sun.moviedb_53.data.model.HotMovie
import com.sun.moviedb_53.data.source.MovieRepository
import com.sun.moviedb_53.data.source.remote.OnFetchDataJsonListener
import com.sun.moviedb_53.utils.Constant
import com.sun.moviedb_53.utils.HotMovieType

class HotMoviePresenter internal constructor(private val repository: MovieRepository?) :
    HotMovieContact.Presenter {

    private var view: HotMovieContact.View? = null

    override fun getHotMovies(page: Int, hotMovieType: HotMovieType) {
        repository?.getMovie(
            page,
            hotMovieType,
            object : OnFetchDataJsonListener<MutableList<HotMovie?>> {
                override fun onSuccess(data: MutableList<HotMovie?>) {
                    view?.onGetMoviesSuccess(data)
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                }
            })
    }

    override fun onStart() {
        getHotMovies(Constant.DEFAULT_PAGE, HotMovieType.POPULAR)
    }

    override fun onStop() {
        this.view = null
    }

    override fun setView(view: HotMovieContact.View?) {
        this.view = view
    }
}
