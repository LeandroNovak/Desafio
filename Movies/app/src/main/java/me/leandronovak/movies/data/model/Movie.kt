package me.leandronovak.movies.data.model

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String,
    val voteAverage: String,
    val genres: List<String>,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String
) {
    companion object {
        @JvmStatic
        @BindingAdapter("image")
        fun bindImage(imageView: ImageView, url: String?) {
            if (url != null && url != "") {
                Picasso.get().load(url).into(imageView)
            }
        }

        @JvmStatic
        @BindingAdapter("yearFromDateString")
        fun bindYearFromDateString(textView: TextView, dateString: String?) {
            if (dateString != null && dateString != "") {
                val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val outputDateFormat = SimpleDateFormat("yyyy", Locale.US)

                val date = inputDateFormat.parse(dateString)
                val year = outputDateFormat.format(date!!)

                textView.text = year


            }
        }
    }
}