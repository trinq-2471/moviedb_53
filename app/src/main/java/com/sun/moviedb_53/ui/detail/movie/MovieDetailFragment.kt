package com.sun.moviedb_53.ui.detail.movie

import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import androidx.core.os.bundleOf
import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseFragment
import com.sun.moviedb_53.data.model.MovieDetail
import com.sun.moviedb_53.data.source.MovieRepository
import com.sun.moviedb_53.data.source.local.MovieLocalDataSource
import com.sun.moviedb_53.data.source.remote.MovieRemoteDataSource
import com.sun.moviedb_53.extensions.loadFromUrl
import com.sun.moviedb_53.utils.Constant
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlin.math.roundToInt


@Suppress("DEPRECATION")
class MovieDetailFragment : BaseFragment(), MovieDetailContact.View {

    private var idMovie: Int? = null
    private var detailPresenter: MovieDetailPresenter? = null

    override fun getLayoutId() = R.layout.fragment_movie_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailPresenter = MovieDetailPresenter(
            MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance(),
                MovieLocalDataSource.getInstance()
            )
        )
        arguments?.let {
            idMovie = it.getInt(ID_MOVIE_DETAIL)
        }
        detailPresenter?.let {
            it.setView(this)
            idMovie?.let { id -> it.getMovieDetail(id) }
        }
    }

    override fun onViewCreated(view: View) {
        onInitView()
    }

    override fun loadContentMovieOnSuccess(movieDetail: MovieDetail) {
        initDataMovieDetail(movieDetail)
    }

    override fun onError(exception: Exception?) {}

    private fun onInitView() {
        val height = requireActivity().windowManager.defaultDisplay.run {
            val size = Point()
            getSize(size)
            size.y
        }

        constraintLayoutBar.apply {
            maxHeight = (height * 0.31).roundToInt()
            minHeight = (height * 0.31).roundToInt()
        }
    }

    private fun initDataMovieDetail(movieDetail: MovieDetail) {
        movieDetail.apply {
            textTitle.text = title
            textOverview.text = resources.getString(R.string.overview) + description
            textRelease.text = releaseDate
            textTagLine.text = tagLine
            ratingBar.rating = rate.toFloat() / 2
            textGenres.text = movieDetail.genres.joinToString(", ") { it.name }
            imagePoster.loadFromUrl(Constant.BASE_URL_IMAGE + photoPoster)
            imageBackground.loadFromUrl(Constant.BASE_URL_IMAGE + photoUrl)
        }
    }

    private fun getHeightDevice(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val metrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
            val usableHeight = metrics.heightPixels
            requireActivity().windowManager.defaultDisplay.getRealMetrics(metrics)
            val realHeight = metrics.heightPixels
            return if (realHeight > usableHeight) realHeight - usableHeight else 0
        }
        return 0
    }

    companion object {
        private const val ID_MOVIE_DETAIL = "ID_MOVIE_DETAIL"

        fun newInstance(id: Int) = MovieDetailFragment().apply {
            arguments = bundleOf(ID_MOVIE_DETAIL to id)
        }
    }
}
