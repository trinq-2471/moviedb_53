package com.sun.moviedb_53.ui.hot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.moviedb_53.R
import com.sun.moviedb_53.data.model.HotMovie
import com.sun.moviedb_53.extensions.loadFromUrl
import com.sun.moviedb_53.utils.Constant
import kotlinx.android.synthetic.main.item_hot_movie.view.*
import kotlinx.android.synthetic.main.item_load_more.view.*

class HotMovieAdapter(private val onItemClick: (HotMovie) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listHotMovie = mutableListOf<HotMovie?>()

    fun setData(newListHotMovie: MutableList<HotMovie?>) {
        listHotMovie.clear()
        listHotMovie = newListHotMovie
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_LOADING) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_load_more, parent, false)
            return LoadItemViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_hot_movie, parent, false)
            return HotMovieViewHolder(view, onItemClick)
        }
    }

    override fun getItemCount() = listHotMovie.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HotMovieViewHolder) {
            listHotMovie[position]?.let { holder.populateItemRow(it) }
        } else if (holder is LoadItemViewHolder) {
            holder.showLoadingView()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listHotMovie.get(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    class HotMovieViewHolder(itemView: View, private val onItemClick: (HotMovie) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun populateItemRow(hotMovie: HotMovie) {
            itemView.apply {
                textHotMovieRate.text = (hotMovie.voteAverage).toString()
                imageHotMovie.loadFromUrl(Constant.BASE_URL_IMAGE + hotMovie.posterPath)
                rateBarHotMovie.rating = hotMovie.voteAverage?.div(2)?.toFloat() ?: -1f
                textHotMovieTittle.text = hotMovie.title
                setOnClickListener {
                    onItemClick(hotMovie)
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

    fun addMovies(movies: MutableList<HotMovie?>) {
        listHotMovie.addAll(movies)
        notifyDataSetChanged()
    }

    fun addMoviesNull() {
        listHotMovie.add(null)
        notifyItemInserted(listHotMovie.size - 1)
    }

    fun removeMoviesLastItem() {
        listHotMovie.removeAt(listHotMovie.size - 1)
        notifyItemRemoved(listHotMovie.size)
    }

    companion object {
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_ITEM = 1
    }
}
