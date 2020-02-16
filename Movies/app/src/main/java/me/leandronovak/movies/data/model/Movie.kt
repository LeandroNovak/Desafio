package me.leandronovak.movies.data.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String,
    val voteAverage: Double,
    val genres: List<String>,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String
) {
    companion object {
        @JvmStatic
        @BindingAdapter("image")
        fun bindImage(imageView: ImageView, url: String?) {
            if (url != null) {
                Picasso.get().load(url).into(imageView)
            }
        }
    }
}