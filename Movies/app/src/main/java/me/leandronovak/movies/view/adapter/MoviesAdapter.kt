package me.leandronovak.movies.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import me.leandronovak.movies.BR
import me.leandronovak.movies.R
import me.leandronovak.movies.data.model.Movie
import me.leandronovak.movies.databinding.ItemMovieBinding

class MoviesAdapter(
    private var moviesList: List<Movie>,
    private val onItemClickListener: ((movie: Movie) -> Unit)
) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MoviesViewHolder(itemView, onItemClickListener)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.bind(moviesList[position])
        holder.binding?.setVariable(BR.movie, movie)
        holder.binding?.executePendingBindings()
    }

    override fun getItemCount() = moviesList.count()

    fun setList(movieList: List<Movie>) {
        this.moviesList = movieList
        notifyDataSetChanged()
    }

    class MoviesViewHolder(
        itemView: View,
        private val onItemClickListener: ((movie: Movie) -> Unit)
    ) : RecyclerView.ViewHolder(itemView) {

        val binding: ItemMovieBinding? = DataBindingUtil.bind(itemView)

        fun bind(movie: Movie) {
            binding?.movie = movie

            itemView.setOnClickListener {
                onItemClickListener.invoke(movie)
            }
        }
    }
}