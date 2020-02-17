package me.leandronovak.movies.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_genre.view.*
import me.leandronovak.movies.R

class GenresAdapter(private var items: List<String>) :
    RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_genre, parent, false)
        return GenresViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setList(genreList: List<String>) {
        this.items = genreList
        notifyDataSetChanged()
    }

    class GenresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String) {
            itemView.text_view.text = item
        }
    }
}