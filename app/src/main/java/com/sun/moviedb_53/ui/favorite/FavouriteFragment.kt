package com.sun.moviedb_53.ui.favorite

import android.view.View
import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseFragment
import com.sun.moviedb_53.data.model.Favorite
import com.sun.moviedb_53.data.source.local.MovieLocalDataSource
import com.sun.moviedb_53.data.source.repository.FavoriteRepository
import com.sun.moviedb_53.extensions.addFragment
import com.sun.moviedb_53.ui.detail.movie.MovieDetailFragment
import kotlinx.android.synthetic.main.fragment_favourite.*
import java.util.*
import kotlin.concurrent.schedule

class FavouriteFragment : BaseFragment(), FavoriteContact.View {

    private var favoritePresenter: FavoritePresenter? = null

    private val favoriteAdapter by lazy {
        FavoriteAdapter({
            addFragment(MovieDetailFragment.newInstance(it), R.id.mFrameMain)
        }, { id, position ->
            removeFavorite(id, position)
        })
    }

    private fun removeFavorite(id: Int, position: Int) {
        favoritePresenter?.deleteFavorite(id)
        favoriteAdapter.removeItem(position)
    }

    override fun getLayoutId() = R.layout.fragment_favourite

    override fun onViewCreated(view: View) {
        onInitRecyclerView()
        favoritePresenter = FavoritePresenter(
            FavoriteRepository.getInstance(MovieLocalDataSource.getInstance(requireActivity()))
        )
        favoritePresenter?.let {
            it.setView(this)
            it.onStart()
        }
        requireActivity().supportFragmentManager.addOnBackStackChangedListener {
            if (isCheckFavorite) {
                favoritePresenter?.onStart()
                isCheckFavorite = false
            }
        }
    }

    override fun onEvent() {
        swipeRefreshFavorite.setOnRefreshListener {
            favoritePresenter?.onStart()
            Timer("refresh", false).schedule(2000) {
                swipeRefreshFavorite.isRefreshing = false
            }
        }
    }

    override fun loadFavoriteOnSuccess(movies: MutableList<Favorite>) {
        favoriteAdapter.setData(movies)
    }

    private fun onInitRecyclerView() {
        recyclerViewFavorite.apply {
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    companion object {
        fun newInstance() = FavouriteFragment()

        var isCheckFavorite = false
    }
}
