package com.sun.moviedb_53.ui.homepage

import android.view.View
import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseFragment

class HomePageFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_home_page

    override fun onViewCreated(view: View) {
        onInitViewPager()
        onInitNav()
        onClick()
    }

    private fun onClick() {}

    private fun onInitNav() {}

    private fun onInitViewPager() {}
}
