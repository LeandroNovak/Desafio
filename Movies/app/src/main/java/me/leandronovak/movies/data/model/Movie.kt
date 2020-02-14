package me.leandronovak.movies.data.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String
) {
    companion object {
        @JvmStatic
        @BindingAdapter("poster")
        fun bindPoster(imageView: ImageView, url: String) {
            Picasso.get().load(url).into(imageView)
        }
    }
}