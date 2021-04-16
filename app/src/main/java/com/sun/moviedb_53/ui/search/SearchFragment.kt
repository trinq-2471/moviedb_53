package com.sun.moviedb_53.ui.search

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseFragment
import com.sun.moviedb_53.data.model.SearchMovie
import com.sun.moviedb_53.data.source.remote.MovieRemoteDataSource
import com.sun.moviedb_53.data.source.repository.MovieRepository
import com.sun.moviedb_53.extensions.addFragment
import com.sun.moviedb_53.ui.detail.movie.MovieDetailFragment
import com.sun.moviedb_53.utils.Constant
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : BaseFragment(), SearchMovieContact.View {

    private lateinit var searchMoviePresenter: SearchMoviePresenter
    private lateinit var searchMovieAdapter: SearchMovieAdapter

    private var page = Constant.DEFAULT_PAGE
    private var isLoading = false

    override fun getLayoutId() = R.layout.fragment_search

    override fun onViewCreated(view: View) {
        initView()
        initData()
    }

    private fun initView() {
        focusEdtSearchMovie()
        imageSearchBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            hideKeyboard()
        }
        imageViewSearch.setOnClickListener {
            page = Constant.DEFAULT_PAGE
            recyclerViewSearchMovie.layoutManager?.scrollToPosition(0)
            searchMoviePresenter.getSearchResult(
                page,
                edtSearchMovie.text.toString()
            )
            hideKeyboard()
        }
        searchMovieAdapter = SearchMovieAdapter {
            addFragment(MovieDetailFragment.newInstance(it), R.id.mFrameMain)
        }
        recyclerViewSearchMovie.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = searchMovieAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val gridLayoutManager = (layoutManager as GridLayoutManager)
                    val totalItemCount = gridLayoutManager.itemCount
                    val lastVisibleItem = gridLayoutManager.findLastCompletelyVisibleItemPosition()
                    if (!isLoading && totalItemCount <= lastVisibleItem + Constant.VISIBLE_THRESHOLD
                    ) {
                        loadMoreData()
                        isLoading = true
                    }
                }
            })
        }
    }

    private fun initData() {
        searchMoviePresenter = SearchMoviePresenter(
            MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance()
            )
        )
        searchMoviePresenter.setView(this)
    }

    private fun loadMoreData() {
        searchMovieAdapter.addMoviesNull()
        page++
        searchMoviePresenter.getSearchResult(page, edtSearchMovie.text.toString())
    }

    private fun focusEdtSearchMovie() {
        edtSearchMovie.requestFocus()
        val inputMethodManager =
            context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            0
        )
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity!!.currentFocus
        if (view == null) {
            view = View(activity)
        }
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onSearchSuccess(listSearchMovie: MutableList<SearchMovie?>) {
        if (listSearchMovie.size == 0 && page == Constant.DEFAULT_PAGE) {
            val toast = Toast.makeText(
                requireContext(),
                context?.getString(R.string.no_result),
                Toast.LENGTH_SHORT
            )
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show()
        } else {
            if (page == 1) {
                searchMovieAdapter.setData(listSearchMovie)
            } else {
                searchMovieAdapter.removeMoviesLastItem()
                searchMovieAdapter.addMovies(listSearchMovie)
                isLoading = false
            }
        }
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(requireContext(), exception?.message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
