package com.sun.moviedb_53.ui.favorite

import android.view.View
import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseFragment

class FavouriteFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_favourite

    override fun onViewCreated(view: View) {}

    companion object {
        fun newInstance() = FavouriteFragment()
    }
}
