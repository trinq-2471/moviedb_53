package com.sun.moviedb_53.ui.homepage

import android.view.View
import androidx.fragment.app.Fragment
import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseFragment
import com.sun.moviedb_53.extensions.addFragment
import com.sun.moviedb_53.extensions.replaceFragment
import com.sun.moviedb_53.ui.search.SearchFragment
import com.sun.moviedb_53.ui.favorite.FavouriteFragment
import com.sun.moviedb_53.ui.genres.GenresFragment
import com.sun.moviedb_53.ui.hot.HotFragment
import com.sun.moviedb_53.utils.ItemBottomNav
import kotlinx.android.synthetic.main.fragment_home_page.*

class HomePageFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_home_page

    override fun onViewCreated(view: View) {
        onInitViewPager()
        onClick()
        onInitNav()
    }

    private fun onClick() {
        edtSearch.setOnClickListener {
            addFragment(SearchFragment.newInstance(), R.id.mFrameMain)
        }
    }

    private fun onInitViewPager() {
        val listFragment = listOf(
            HotFragment.newInstance(),
            GenresFragment.newInstance(),
            FavouriteFragment.newInstance()
        )
        fragmentManager?.let {
            viewPagerHomePage.adapter = HomePageAdapter(it, listFragment)
        }
    }

    private fun onInitNav() {
        bottomNavHomePage.setOnNavigationItemSelectedListener { it ->
            when (it.itemId) {
                R.id.itemHotPage -> {
                    viewPagerHomePage.currentItem = ItemBottomNav.HOT_ITEM.position
                    true
                }
                R.id.itemGenresPage -> {
                    viewPagerHomePage.currentItem = ItemBottomNav.GENRES_ITEM.position
                    true
                }
                R.id.itemFavoritePage -> {
                    viewPagerHomePage.currentItem = ItemBottomNav.FAVORITE_ITEM.position
                    true
                }
                else -> false
            }
        }
    }

    companion object {
        fun newInstance() = HomePageFragment()
    }
}
