package com.sun.moviedb_53.data.source

import com.sun.moviedb_53.data.model.HotMovie
import com.sun.moviedb_53.data.source.remote.OnFetchDataJsonListener
import com.sun.moviedb_53.utils.HotMovieType

interface MovieDataSource {

    interface Local

    interface Remote {
        fun getHotMovies(
                page: Int,
                hotMovieType: HotMovieType,
                listener: OnFetchDataJsonListener<MutableList<HotMovie?>>
        )
    }
}
