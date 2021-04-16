package com.sun.moviedb_53.ui.genres

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.moviedb_53.R
import com.sun.moviedb_53.data.model.Genre
import kotlinx.android.synthetic.main.item_genres_selected.view.*

class GenresSelectedAdapter(
    private var onClickListener: (Int?, Int) -> Unit
) :
    RecyclerView.Adapter<GenresSelectedAdapter.GenresSelectedViewHolder>() {

    private var genresSelected = mutableListOf<Genre?>()

    fun setData(newListGenresSelected: MutableList<Genre?>) {
        genresSelected = newListGenresSelected
        notifyDataSetChanged()
    }

    fun removeSelectedGenres(position: Int) {
        genresSelected.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, genresSelected.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresSelectedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_genres_selected, parent, false)
        return GenresSelectedViewHolder(view, onClickListener)
    }

    override fun getItemCount() = genresSelected.size

    override fun onBindViewHolder(holder: GenresSelectedViewHolder, position: Int) {
        holder.bindData(genresSelected)
    }

    class GenresSelectedViewHolder(itemView: View, val onClickListener: (Int?, Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        fun bindData(
            listGenre: MutableList<Genre?>
        ) {
            itemView.apply {
                textGenresSelected.text = listGenre[adapterPosition]?.name
                setOnClickListener {
                    if (listGenre.size != 1 && adapterPosition in 0..listGenre.size)
                        onClickListener(
                            listGenre[adapterPosition]?.positionSelected,
                            adapterPosition
                        )
                }
            }
        }
    }
}
