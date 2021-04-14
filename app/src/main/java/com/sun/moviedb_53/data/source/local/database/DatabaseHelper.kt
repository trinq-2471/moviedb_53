package com.sun.moviedb_53.data.source.local.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sun.moviedb_53.data.source.local.database.table.FavoriteTable

class DatabaseHelper private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(FavoriteTable.DB_MOVIE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) = Unit

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "movie.db"

        private var instance: DatabaseHelper? = null

        fun getDatabaseHelper(context: Context): DatabaseHelper = instance ?: synchronized(this) {
            instance ?: DatabaseHelper(context).also {
                instance = it
            }
        }
    }
}
