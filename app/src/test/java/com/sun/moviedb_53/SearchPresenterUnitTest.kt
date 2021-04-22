package com.sun.moviedb_53

import com.sun.moviedb_53.data.model.SearchMovie
import com.sun.moviedb_53.data.source.remote.OnFetchDataJsonListener
import com.sun.moviedb_53.data.source.repository.MovieRepository
import com.sun.moviedb_53.ui.search.SearchMovieContact
import com.sun.moviedb_53.ui.search.SearchMoviePresenter
import com.sun.moviedb_53.utils.FakeData
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception
import org.mockito.kotlin.doAnswer

@RunWith(MockitoJUnitRunner::class)
class SearchPresenterUnitTest {

    @Mock
    private lateinit var view: SearchMovieContact.View

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var exception: Exception

    private lateinit var searchMoviePresenter: SearchMoviePresenter
    private lateinit var listSearchMovie: MutableList<SearchMovie?>

    @Before
    fun setUp() {
        listSearchMovie = FakeData.SEARCH_MODEL
        searchMoviePresenter = SearchMoviePresenter(movieRepository)
        searchMoviePresenter.setView(view)
    }

    @Test
    fun `get data search result success`() {
        `when`(
            movieRepository.onGetSearchResult(
                anyInt(),
                anyString(),
                org.mockito.kotlin.any()
            )
        ).doAnswer {
            (it.arguments[2] as OnFetchDataJsonListener<MutableList<SearchMovie?>>).onSuccess(
                listSearchMovie
            )
        }
        searchMoviePresenter.getSearchResult(FakeData.PAGE_SEARCH, FakeData.QUERY_SEARCH)
        verify(view).onSearchSuccess(listSearchMovie)
    }

    @Test
    fun `get data search result error`() {
        `when`(
            movieRepository.onGetSearchResult(
                anyInt(),
                anyString(),
                org.mockito.kotlin.any()
            )
        ).doAnswer {
            (it.arguments[2] as OnFetchDataJsonListener<MutableList<SearchMovie?>>).onError(
                exception
            )
        }
        searchMoviePresenter.getSearchResult(FakeData.PAGE_SEARCH, FakeData.QUERY_SEARCH)
        verify(view).onError(exception)
    }
}
