package com.sun.moviedb_53.utils

interface BasePresenter<T> {
    fun onStart()
    fun onStop()
    fun setView(view: T?)
}
