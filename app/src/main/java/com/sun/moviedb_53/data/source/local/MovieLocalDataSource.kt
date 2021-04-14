package com.sun.moviedb_53.data.source.local

import android.content.Context
import com.sun.moviedb_53.data.model.Favorite
import com.sun.moviedb_53.data.source.MovieDataSource
import com.sun.moviedb_53.data.source.local.database.DatabaseHelper
import com.sun.moviedb_53.data.source.local.database.dao.FavoriteDaoImpl

class MovieLocalDataSource private constructor(private val favoriteDaoImpl: FavoriteDaoImpl) :
    MovieDataSource.Local {

    override fun saveMovie(favorite: Favorite) = favoriteDaoImpl.insertFavorite(favorite)

    override fun getListFavorite() = favoriteDaoImpl.getListFavorite()

    override fun deleteFavorite(idMovie: Int) = favoriteDaoImpl.deleteFavorite(idMovie)

    override fun checkFavorite(idMovie: Int) = favoriteDaoImpl.checkFavorite(idMovie)

    companion object {
        var instance: MovieLocalDataSource? = null

        fun getInstance(context: Context): MovieDataSource.Local =
            instance ?: MovieLocalDataSource(
                FavoriteDaoImpl.getInstance(
                    DatabaseHelper.getDatabaseHelper(context)
                )
            ).also { instance = it }
    }
}
