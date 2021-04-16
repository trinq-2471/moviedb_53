package com.sun.moviedb_53.ui.genres

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseFragment
import com.sun.moviedb_53.data.model.Genre
import com.sun.moviedb_53.data.model.GenresMovie
import com.sun.moviedb_53.data.source.MovieRepository
import com.sun.moviedb_53.data.source.local.MovieLocalDataSource
import com.sun.moviedb_53.data.source.remote.MovieRemoteDataSource
import com.sun.moviedb_53.utils.Constant
import kotlinx.android.synthetic.main.fragment_genres.*

class GenresFragment : BaseFragment(), GenresContact.View {

    private lateinit var genresPresenter: GenresPresenter
    private lateinit var adapterGenres: GenresAdapter
    private lateinit var adapterGenresSelected: GenresSelectedAdapter

    private var listSelectedGenres = mutableListOf<Genre?>()

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
        recyclerViewGenres.adapter = adapterGenres
        recyclerViewGenresSelected.adapter = adapterGenresSelected
    }

    private fun initData() {
        genresPresenter = GenresPresenter(
            MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance(),
                MovieLocalDataSource.getInstance(requireContext())
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

    private fun onClickGenres(genres: Genre, position: Int) {
        adapterGenres.selectedGenres(position)
        genres.positionSelected = position
        listSelectedGenres.add(genres)
        adapterGenresSelected.setData(listSelectedGenres)
        genresPresenter.getGenresMovie(Constant.DEFAULT_PAGE, getQueryGenres())
    }

    private fun onClickSelectedGenres(positionSelected: Int?, positionRemove: Int) {
        adapterGenres.unselectedGenres(positionSelected)
        adapterGenresSelected.removeSelectedGenres(positionRemove)
        genresPresenter.getGenresMovie(Constant.DEFAULT_PAGE, getQueryGenres())
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
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(requireContext(), exception?.message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = GenresFragment()
    }
}
