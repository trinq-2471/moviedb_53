package com.sun.moviedb_53.ui.genres

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseFragment
import com.sun.moviedb_53.data.model.Genre
import com.sun.moviedb_53.data.model.GenresMovie
import com.sun.moviedb_53.data.source.remote.MovieRemoteDataSource
import com.sun.moviedb_53.data.source.repository.MovieRepository
import com.sun.moviedb_53.extensions.addFragment
import com.sun.moviedb_53.ui.detail.movie.MovieDetailFragment
import com.sun.moviedb_53.ui.genres.adapter.GenresMovieAdapter
import com.sun.moviedb_53.utils.Constant
import kotlinx.android.synthetic.main.fragment_genres.*

class GenresFragment : BaseFragment(), GenresContact.View {

    private lateinit var genresPresenter: GenresPresenter
    private lateinit var adapterGenres: GenresAdapter
    private lateinit var adapterGenresSelected: GenresSelectedAdapter
    private lateinit var adapterGenresMovie: GenresMovieAdapter

    private var listSelectedGenres = mutableListOf<Genre?>()
    private var page = Constant.DEFAULT_PAGE
    private var isLoading = false

    private val onClickGenres = fun(genres: Genre, position: Int) {
        onClickGenres(genres, position)
    }
    private val onClickSelectedGenres = fun(positionSelected: Int?, positionRemove: Int) {
        onClickSelectedGenres(positionSelected, positionRemove)
    }

    override fun getLayoutId() = R.layout.fragment_genres

    override fun onViewCreated(view: View) {
        initView()
        initData()
    }

    private fun initView() {
        adapterGenres = GenresAdapter(requireContext(), onClickGenres)
        adapterGenresSelected = GenresSelectedAdapter(onClickSelectedGenres)
        adapterGenresMovie = GenresMovieAdapter {
            addFragment(MovieDetailFragment.newInstance(it), R.id.mFrameMain)
        }
        recyclerViewGenres.adapter = adapterGenres
        recyclerViewGenresSelected.adapter = adapterGenresSelected
        recyclerViewMovieGenres.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = adapterGenresMovie

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
        genresPresenter = GenresPresenter(
            MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance()
            )
        )
        genresPresenter.setView(this)
        genresPresenter.onStart()
    }

    private fun getQueryGenres(): String {
        var query = ""
        listSelectedGenres.forEach { genres ->
            query += ", ${genres?.id}"
        }
        return query
    }

    private fun loadMoreData() {
        adapterGenresMovie.addMoviesNull()
        page++
        genresPresenter.getGenresMovie(page, getQueryGenres())
    }

    private fun onClickGenres(genres: Genre, position: Int) {
        page = Constant.DEFAULT_PAGE
        recyclerViewMovieGenres.layoutManager?.scrollToPosition(0)
        adapterGenres.selectedGenres(position)
        genres.positionSelected = position
        listSelectedGenres.add(genres)
        adapterGenresSelected.setData(listSelectedGenres)
        genresPresenter.getGenresMovie(page, getQueryGenres())
    }

    private fun onClickSelectedGenres(positionSelected: Int?, positionRemove: Int) {
        page = Constant.DEFAULT_PAGE
        recyclerViewMovieGenres.layoutManager?.scrollToPosition(0)
        adapterGenres.unselectedGenres(positionSelected)
        adapterGenresSelected.removeSelectedGenres(positionRemove)
        genresPresenter.getGenresMovie(page, getQueryGenres())
    }

    override fun onGetGenresSuccess(listGenres: MutableList<Genre?>) {
        listGenres[0]?.let {
            it.isSelected = true
            it.positionSelected = 0
        }
        adapterGenres.setData(listGenres)
        listSelectedGenres.add(listGenres[0])
        adapterGenresSelected.setData(listSelectedGenres)
        genresPresenter.getGenresMovie(Constant.DEFAULT_PAGE, listGenres[0]?.id.toString())
    }

    override fun onGetGenresMovieSuccess(listMovieGenres: MutableList<GenresMovie?>) {
        if (page == 1) {
            adapterGenresMovie.setData(listMovieGenres)
        } else {
            adapterGenresMovie.removeMoviesLastItem()
            adapterGenresMovie.addMovies(listMovieGenres)
            isLoading = false
        }
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(requireContext(), exception?.message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = GenresFragment()
    }
}
