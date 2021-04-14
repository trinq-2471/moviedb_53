package com.sun.moviedb_53.ui.genres

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseFragment
import com.sun.moviedb_53.data.model.Genres
import kotlinx.android.synthetic.main.fragment_genres.*

class GenresFragment : BaseFragment() {

    lateinit var adapterGenres : GenresAdapter
    lateinit var adapterGenresSelected : GenresSelectedAdapter
    var listSelectedGenres = mutableListOf<Genres>()

    private val onClickGenres = fun (genres: Genres, position : Int){
        adapterGenres.selectedGenres(position)
        genres.positionSelected = position
        adapterGenresSelected.addGenres(genres)
    }

    private val onClickSelectedGenres = fun ( positionSelected : Int?, positionRemove : Int){
        adapterGenres.unselectedGenres(positionSelected)
        adapterGenresSelected.removeGenres(positionRemove)
    }

    override fun getLayoutId() = R.layout.fragment_genres

    override fun onViewCreated(view: View) {

        var listGenres = mutableListOf<Genres>(
            Genres(1,"Action"),
            Genres(1,"Adventure"),
            Genres(1,"Animation"),
            Genres(1,"Comedy"),
            Genres(1,"Crime"),
            Genres(1,"Documentary"),
            Genres(1,"Drama"),
            Genres(1,"Family"),
        )

        listGenres[0].isClick = true
        listGenres[0].positionSelected = 0
        adapterGenres = GenresAdapter(requireActivity(),listGenres, onClickGenres )
        recyclerViewGenres.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = adapterGenres
        }

        listSelectedGenres.add(listGenres[0])

        adapterGenresSelected = GenresSelectedAdapter(listSelectedGenres, onClickSelectedGenres)
        recyclerViewGenresSelected.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = adapterGenresSelected
        }
    }

    companion object {
        fun newInstance() = GenresFragment()
    }
}
