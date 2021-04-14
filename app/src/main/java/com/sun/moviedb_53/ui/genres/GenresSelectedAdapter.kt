package com.sun.moviedb_53.ui.genres

import android.media.tv.TvContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.moviedb_53.R
import com.sun.moviedb_53.data.model.Genres
import kotlinx.android.synthetic.main.item_genres_selected.view.*

class GenresSelectedAdapter(
    private var listGenresSelected: MutableList<Genres>,
    private var onClickListener: (Int?, Int) -> Unit
) :
    RecyclerView.Adapter<GenresSelectedAdapter.GenresSelectedViewHolder>() {

    fun addGenres(genres: Genres) {
        listGenresSelected.add(genres)
        notifyDataSetChanged()
    }

    fun removeGenres(position: Int) {
        if (listGenresSelected.size > 1){
            listGenresSelected.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresSelectedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_genres_selected, parent, false)
        return GenresSelectedAdapter.GenresSelectedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listGenresSelected.size
    }

    override fun onBindViewHolder(holder: GenresSelectedViewHolder, position: Int) {
        val genres = listGenresSelected[position]
        holder.bindData(listGenresSelected, position, onClickListener)
    }

    class GenresSelectedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(listGenres: MutableList<Genres>, position: Int, onClickListener : (Int?,Int) -> Unit ) {

            itemView.apply {
                tvGenresSelected.text = listGenres[position].name
                setOnClickListener {
                    if (listGenres.size != 1)
                    onClickListener(listGenres[position].positionSelected, position)
                }
            }
        }
    }
}