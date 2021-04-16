package com.sun.moviedb_53.ui.genres

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sun.moviedb_53.R
import com.sun.moviedb_53.data.model.Genre
import kotlinx.android.synthetic.main.item_genres.view.*

class GenresAdapter(
    private val context: Context,
    private var onClickListener: (Genre, Int) -> Unit
) : RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {

    private var genres = mutableListOf<Genre?>()

    fun setData(newListGenres: MutableList<Genre?>) {
        genres = newListGenres
        notifyDataSetChanged()
    }

    fun selectedGenres(position: Int) {
        genres[position]?.isSelected = true
        notifyItemChanged(position)
    }

    fun unselectedGenres(position: Int?) {
        position?.let {
            genres[it]?.isSelected = false
            notifyItemChanged(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genres, parent, false)
        return GenresViewHolder(view, onClickListener)
    }

    override fun getItemCount() = genres.size

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        genres[position]?.let { holder.bindData(context, it) }
    }

    class GenresViewHolder(itemView: View, val onClickListener: (Genre, Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun bindData(
            context: Context,
            genre: Genre
        ) {
            itemView.let {
                it.textGenres.text = genre.name
                it.setOnClickListener {
                    if (!genre.isSelected) onClickListener(genre, adapterPosition)
                }
                if (genre.isSelected) {
                    it.linearLayoutGenres.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.red
                        )
                    )
                    it.textGenres.setTextColor(ContextCompat.getColor(context, R.color.white))
                } else {
                    it.linearLayoutGenres.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.yellow_dark
                        )
                    )
                    it.textGenres.setTextColor(ContextCompat.getColor(context, R.color.black))
                }
            }
        }
    }
}
