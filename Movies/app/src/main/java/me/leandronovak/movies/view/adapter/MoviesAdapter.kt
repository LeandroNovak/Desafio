package me.leandronovak.movies.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_movie.view.*
import me.leandronovak.movies.R
import me.leandronovak.movies.data.model.Movie

class MoviesAdapter(
    private val movies: List<Movie>,
    private val onItemClickListener: ((movie: Movie) -> Unit)
) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)

        return MoviesViewHolder(itemView, onItemClickListener)
    }

    override fun getItemCount() = movies.count()

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bindView(movies[position])
    }

    class MoviesViewHolder(
        itemView: View,
        private val onItemClickListener: ((movie: Movie) -> Unit)
    ) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.textTitle

        fun bindView(movie: Movie) {
            title.text = movie.title

            itemView.setOnClickListener{
                onItemClickListener.invoke(movie)
            }
        }
    }
}