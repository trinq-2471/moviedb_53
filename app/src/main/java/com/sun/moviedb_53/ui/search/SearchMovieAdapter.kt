package com.sun.moviedb_53.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.moviedb_53.R
import com.sun.moviedb_53.data.model.SearchMovie
import com.sun.moviedb_53.extensions.loadFromUrl
import com.sun.moviedb_53.utils.Constant

import kotlinx.android.synthetic.main.item_load_more.view.*
import kotlinx.android.synthetic.main.item_search_movie.view.*

class SearchMovieAdapter(private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listSearchMovie = mutableListOf<SearchMovie?>()

    fun setData(newListSearchMovie: MutableList<SearchMovie?>) {
        listSearchMovie.clear()
        listSearchMovie = newListSearchMovie
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_SEARCH_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_movie, parent, false)
            return SearchMovieViewHolder(view, onItemClick)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_load_more, parent, false)
            return LoadItemViewHolder(view)
        }
    }

    override fun getItemCount() = listSearchMovie.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchMovieViewHolder) {
            listSearchMovie[position]?.let { holder.bindData(it) }
        } else if (holder is LoadItemViewHolder) {
            holder.showLoadingView()
        }
    }

    override fun getItemViewType(position: Int): Int {
        listSearchMovie[position]?.let {
            return VIEW_TYPE_SEARCH_ITEM
        }
        return VIEW_TYPE_SEARCH_LOADING
    }

    class SearchMovieViewHolder(itemView: View, val onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        fun bindData(searchMovie: SearchMovie) {
            itemView.apply {
                imageSearchMovie.loadFromUrl(Constant.BASE_URL_IMAGE + searchMovie.posterPath)
                textSearchMovieTitle.text = searchMovie.title
                setOnClickListener {
                    searchMovie.id?.let { idMovie -> onItemClick(idMovie) }
                }
            }
        }
    }

    class LoadItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun showLoadingView() {
            itemView.progressBarLoadMore.isEnabled = true
        }
    }

    fun addMovies(movies: MutableList<SearchMovie?>) {
        listSearchMovie.addAll(movies)
        notifyDataSetChanged()
    }

    fun addMoviesNull() {
        listSearchMovie.add(null)
        notifyItemInserted(listSearchMovie.size - 1)
    }

    fun removeMoviesLastItem() {
        listSearchMovie.removeAt(listSearchMovie.size - 1)
        notifyItemRemoved(listSearchMovie.size)
    }

    companion object {
        const val VIEW_TYPE_SEARCH_LOADING = 0
        const val VIEW_TYPE_SEARCH_ITEM = 1
    }
}