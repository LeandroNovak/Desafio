package me.leandronovak.movies.data.rest

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import me.leandronovak.movies.data.model.Movie

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
    //TODO: Update default posterUrl
    fun getMovieModel() = Movie (
        id = this.id,
        title = this.title ?: "",
        posterUrl = this.posterUrl ?: "",
        backdropUrl = this.backdropUrl ?: "",
        voteAverage = this.voteAverage ?: 0.0,
        genres = this.genres ?: emptyList(),
        originalTitle = this.originalTitle ?: "",
        overview = this.overview ?: "",
        releaseDate = this.releaseDate ?: ""
    )
}