package me.leandronovak.movies.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.leandronovak.movies.data.ApiService
import me.leandronovak.movies.data.model.Movie
import me.leandronovak.movies.data.rest.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsViewModel : ViewModel() {
    val movieLiveData: MutableLiveData<Movie> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    fun getMovie(id: Int) {
        isLoading.value = true
        ApiService.movieService.getMovie(id).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { movieResponse ->
                        val movie = movieResponse.getMovieModel()
                        movieLiveData.value = movie

                        isLoading.value = false
                    }
                } else {
                    error.value = "error"
                    Log.e("GET DETAILS", response.code().toString())
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                error.value = t.message
                Log.e("GET DETAILS", t.message!!)
            }
        })
    }
}