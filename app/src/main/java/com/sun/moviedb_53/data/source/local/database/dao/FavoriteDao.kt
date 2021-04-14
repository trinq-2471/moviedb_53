package com.sun.moviedb_53.data.source.local.database.dao

import com.sun.moviedb_53.data.model.Favorite

interface FavoriteDao {
    fun insertFavorite(favorite: Favorite): Boolean
    fun deleteFavorite(id: Int): Boolean
    fun checkFavorite(id: Int): Boolean
    fun getListFavorite(): MutableList<Favorite>
}
