package com.sun.moviedb_53.ui.detail.movie

import com.sun.moviedb_53.data.model.Actor
import com.sun.moviedb_53.data.model.HotMovie
import com.sun.moviedb_53.data.model.MovieDetail
import com.sun.moviedb_53.data.source.remote.OnFetchDataJsonListener
import com.sun.moviedb_53.data.source.repository.FavoriteRepository
import com.sun.moviedb_53.data.source.repository.MovieRepository
import com.sun.moviedb_53.utils.FakeData
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.verify

@Suppress("UNCHECKED_CAST")
@RunWith(MockitoJUnitRunner::class)
class MovieDetailPresenterTest {

    @Mock
    private lateinit var view: MovieDetailContact.View

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var favoriteRepository: FavoriteRepository

    @Mock
    private lateinit var exception: Exception

    private lateinit var presenter: MovieDetailPresenter
    private lateinit var listActor: List<Actor>
    private lateinit var listRecommendation: List<HotMovie>

    private val detail = FakeData.MOVIE_DETAIL

    @Before
    fun setUp() {
        presenter = MovieDetailPresenter(repository, favoriteRepository)
        presenter.setView(view)
        listActor = mutableListOf()
        listRecommendation = mutableListOf()
    }

    @Test
    fun `get data detail movie success`() {
        `when`(
            repository.getMovieDetails(
                ArgumentMatchers.anyInt(),
                any()
            )
        ).thenAnswer {
            (it.arguments[1] as OnFetchDataJsonListener<MovieDetail>).onSuccess(
                detail
            )
        }
        presenter.getMovieDetail(FakeData.ID_MOVIE_DETAIL)
        verify(view).loadContentMovieOnSuccess(detail)
    }

    @Test
    fun `get data detail movie error`() {
        `when`(
            repository.getMovieDetails(
                ArgumentMatchers.anyInt(),
                any()
            )
        ).doAnswer {
            (it.arguments[1] as OnFetchDataJsonListener<MovieDetail>).onError(
                exception
            )
        }
        presenter.getMovieDetail(FakeData.ID_MOVIE_DETAIL)
        verify(view).onError(exception)
    }

    @Test
    fun `get data actor success`() {
        `when`(
            repository.getListActorInMovieDetail(
                ArgumentMatchers.anyInt(),
                any()
            )
        ).doAnswer {
            (it.arguments[1] as OnFetchDataJsonListener<List<Actor>>).onSuccess(
                listActor
            )
        }
        presenter.getActorInMovieDetail(FakeData.ID_MOVIE_DETAIL)
        verify(view).loadListActorOnSuccess(listActor)
    }

    @Test
    fun `get data actor error`() {
        `when`(
            repository.getListActorInMovieDetail(
                ArgumentMatchers.anyInt(),
                any()
            )
        ).doAnswer {
            (it.arguments[1] as OnFetchDataJsonListener<List<Actor>>).onError(
                exception
            )
        }
        presenter.getActorInMovieDetail(FakeData.ID_MOVIE_DETAIL)
        verify(view).onError(exception)
    }

    @Test
    fun `get data recommend movie success`() {
        `when`(
            repository.getListRecommendationsInMovieDetail(
                ArgumentMatchers.anyInt(),
                any()
            )
        ).doAnswer {
            (it.arguments[1] as OnFetchDataJsonListener<List<HotMovie>>).onSuccess(
                listRecommendation
            )
        }
        presenter.getListMovieRecommendations(FakeData.ID_MOVIE_DETAIL)
        verify(view).loadRecommendationOnSuccess(listRecommendation)
    }

    @Test
    fun `get data recommend movie error`() {
        `when`(
            repository.getListRecommendationsInMovieDetail(
                ArgumentMatchers.anyInt(),
                any()
            )
        ).doAnswer {
            (it.arguments[1] as OnFetchDataJsonListener<List<HotMovie>>).onError(
                exception
            )
        }
        presenter.getListMovieRecommendations(FakeData.ID_MOVIE_DETAIL)
        verify(view).onError(exception)
    }
}
