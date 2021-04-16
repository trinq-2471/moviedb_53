package com.sun.moviedb_53.ui.genres.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.moviedb_53.R
import com.sun.moviedb_53.data.model.GenresMovie
import com.sun.moviedb_53.extensions.loadFromUrl
import com.sun.moviedb_53.utils.Constant
import kotlinx.android.synthetic.main.item_genres_movie.view.*
import kotlinx.android.synthetic.main.item_load_more.view.*

class GenresMovieAdapter(private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listGenresMovie = mutableListOf<GenresMovie?>()

    fun setData(newListGenresMovie: MutableList<GenresMovie?>) {
        listGenresMovie = newListGenresMovie
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_GENRE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_genres_movie, parent, false)
            return GenresMovieViewHolder(view, onItemClick)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_load_more, parent, false)
            return LoadItemGridViewHolder(view)
        }
    }

    override fun getItemCount() = listGenresMovie.size

    override fun getItemViewType(position: Int): Int {
        listGenresMovie[position]?.let {
            return VIEW_TYPE_GENRE_ITEM
        }
        return VIEW_TYPE_LOADING
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GenresMovieViewHolder) {
            listGenresMovie[position]?.let { holder.bindData(it) }
        } else if (holder is LoadItemGridViewHolder) {
            holder.showLoadingView()
        }
    }

    class GenresMovieViewHolder(itemView: View, val onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        fun bindData(genresMovie: GenresMovie) {
            itemView.apply {
                imageGenresMovie.loadFromUrl(Constant.BASE_URL_IMAGE + genresMovie.posterPath)
                textGenresMovieTitle.text = genresMovie.title
                setOnClickListener {
                    genresMovie.id?.let { idMovie -> onItemClick(idMovie) }
                }
            }
        }
    }

    class LoadItemGridViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun showLoadingView() {
            itemView.progressBarLoadMore.isEnabled = true
        }
    }

    fun addMovies(genres: MutableList<GenresMovie?>) {
        listGenresMovie.addAll(genres)
        notifyDataSetChanged()
    }

    fun addMoviesNull() {
        listGenresMovie.add(null)
        notifyItemInserted(listGenresMovie.size - 1)
    }

    fun removeMoviesLastItem() {
        listGenresMovie.removeAt(listGenresMovie.size - 1)
        notifyItemRemoved(listGenresMovie.size)
    }

    companion object {
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_GENRE_ITEM = 1
    }
}
