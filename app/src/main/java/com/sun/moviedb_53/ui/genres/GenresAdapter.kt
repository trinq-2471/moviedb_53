package com.sun.moviedb_53.ui.genres

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sun.moviedb_53.R
import com.sun.moviedb_53.data.model.Genres
import kotlinx.android.synthetic.main.item_genres.view.*

class GenresAdapter(
    private val context: Context,
    private var listGenres: MutableList<Genres>,
    private var onClickListener : (Genres, Int) -> Unit
) : RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {

    fun selectedGenres (position: Int) {
        listGenres[position].isClick = true
        notifyItemChanged(position)
    }

    fun unselectedGenres(position: Int?){
        position?.let {
            listGenres[it].isClick = false
            notifyItemChanged(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genres, parent, false)
        return GenresViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listGenres.size
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        listGenres[position].let { holder.bindData(context, it, position, onClickListener) }
    }

    class GenresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(
            context: Context,
            genres: Genres,
            position: Int,
            onClickListener: (Genres, Int) -> Unit
        ) {
            itemView.let {
                it.tvGenres.apply {
                    text = genres.name
                }
                it.setOnClickListener {
                    if (!genres.isClick){
                        onClickListener(genres,position)
                    }
                }
                if (genres.isClick) {
                    it.linearLayoutGenres.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.red
                        )
                    )
                    it.tvGenres.setTextColor(ContextCompat.getColor(context, R.color.white))
                } else {
                    it.linearLayoutGenres.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.yellow_dark
                        )
                    )
                    it.tvGenres.setTextColor(ContextCompat.getColor(context, R.color.black))
                }
            }
        }
    }
}