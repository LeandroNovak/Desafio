package me.leandronovak.movies.data

import retrofit2.Call
import me.leandronovak.movies.data.rest.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {
    @GET("movies")
    fun getMoviesList(): Call<List<MovieResponse>>

    @GET("movies/{id}")
    fun getMovie(@Path("id") id: Int): Call<MovieResponse>
}