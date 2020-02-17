package me.leandronovak.movies.data.rest

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import me.leandronovak.movies.data.model.Movie
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@JsonClass(generateAdapter = true)
data class MovieResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String?,
    @Json(name = "poster_url")
    val posterUrl: String?,
    @Json(name = "backdrop_url")
    val backdropUrl: String?,
    @Json(name = "vote_average")
    val voteAverage: Double?,
    @Json(name = "genres")
    val genres: List<String>?,
    @Json(name = "original_title")
    val originalTitle: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "release_date")
    val releaseDate: String?
) {
    fun getMovieModel() = Movie (
        id = this.id,
        title = this.title ?: "Unavailable",
        posterUrl = this.posterUrl ?: "",
        backdropUrl = this.backdropUrl ?: "",
        voteAverage = this.voteAverage.toString(),
        genres = this.genres ?: emptyList(),
        originalTitle = this.originalTitle ?: "Unavailable",
        overview = this.overview ?: "Unavailable",
        releaseDate = releaseDate ?: ""
    )
}