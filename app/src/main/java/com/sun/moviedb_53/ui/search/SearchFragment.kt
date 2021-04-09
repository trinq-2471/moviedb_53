package com.sun.moviedb_53.ui.search

import android.view.View
import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseFragment

class SearchFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_search

    override fun onViewCreated(view: View) {}

    companion object {
        fun newInstance() = SearchFragment()
    }
}
