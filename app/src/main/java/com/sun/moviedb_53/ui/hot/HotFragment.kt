package com.sun.moviedb_53.ui.hot

import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseFragment
import com.sun.moviedb_53.data.model.HotMovie
import com.sun.moviedb_53.data.source.repository.MovieRepository
import com.sun.moviedb_53.data.source.remote.MovieRemoteDataSource
import com.sun.moviedb_53.extensions.addFragment
import com.sun.moviedb_53.ui.detail.movie.MovieDetailFragment
import com.sun.moviedb_53.utils.Constant
import com.sun.moviedb_53.utils.HotMovieType
import kotlinx.android.synthetic.main.fragment_hot.*

class HotFragment : BaseFragment(), HotMovieContact.View {

    private lateinit var hotMoviePresenter: HotMoviePresenter
    private var page = Constant.DEFAULT_PAGE
    private var hotMovieType = HotMovieType.POPULAR
    private var isLoading = false
    private val adapterHotMovie by lazy {
        HotMovieAdapter {
            it.id?.apply {
                addFragment(MovieDetailFragment.newInstance(this), R.id.mFrameMain)
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_hot

    override fun onViewCreated(view: View) {
        initView()
        initData()
    }

    private fun initView() {
        initRecyclerView()
        setOnClickButton()
        initSwipeRefresh()
    }

    private fun initData() {
        hotMoviePresenter = HotMoviePresenter(
            MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance()
            )
        )
        hotMoviePresenter.setView(this)
        hotMoviePresenter.onStart()
    }

    private fun initRecyclerView() {
        recyclerViewHotMovie.apply {
            setHasFixedSize(true)
            adapter = adapterHotMovie
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    val totalItemCount = linearLayoutManager?.itemCount?.minus(1)
                    val lastVisibleItem =
                        linearLayoutManager?.findLastCompletelyVisibleItemPosition()
                    if (!isLoading && totalItemCount == lastVisibleItem) {
                        loadMoreData()
                        isLoading = true
                    }
                }
            })
        }
    }

    private fun setOnClickButton() {
        btnPopular.setOnClickListener {
            changeDataMovie(HotMovieType.POPULAR)
            setButtonClick(btnPopular)
            setButtonNotClick(btnTopRate)
            setButtonNotClick(btnUpComming)
        }
        btnTopRate.setOnClickListener {
            changeDataMovie(HotMovieType.TOP_RATED)
            setButtonClick(btnTopRate)
            setButtonNotClick(btnPopular)
            setButtonNotClick(btnUpComming)
        }
        btnUpComming.setOnClickListener {
            changeDataMovie(HotMovieType.UP_COMING)
            setButtonClick(btnUpComming)
            setButtonNotClick(btnTopRate)
            setButtonNotClick(btnUpComming)
        }
    }

    private fun initSwipeRefresh() {
        swipeRefreshHotMovie.setOnRefreshListener {
            changeDataMovie(hotMovieType)
        }
    }

    private fun changeDataMovie(typeHotMovie: HotMovieType) {
        recyclerViewHotMovie.layoutManager?.scrollToPosition(0)
        page = Constant.DEFAULT_PAGE
        hotMovieType = typeHotMovie
        hotMoviePresenter.getHotMovies(page, hotMovieType)
    }

    private fun setButtonClick(button: Button) {
        button.apply {
            background = ResourcesCompat.getDrawable(
                resources, R.drawable.custom_hot_screen_button, null
            )
            setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }

    private fun setButtonNotClick(button: Button) {
        button.apply {
            background = ResourcesCompat.getDrawable(
                resources, R.drawable.custom_hot_screen_button_not_click, null
            )
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
    }

    private fun loadMoreData() {
        adapterHotMovie.addMoviesNull()
        page++
        hotMoviePresenter.getHotMovies(page, hotMovieType)
    }

    override fun onGetMoviesSuccess(movies: MutableList<HotMovie?>) {
        if (page == Constant.DEFAULT_PAGE) {
            adapterHotMovie.setData(movies)
            swipeRefreshHotMovie.isRefreshing = false
        } else {
            adapterHotMovie.apply {
                removeMoviesLastItem()
                addMovies(movies)
            }
            isLoading = false
        }
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(activity, exception?.message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = HotFragment()
    }
}
