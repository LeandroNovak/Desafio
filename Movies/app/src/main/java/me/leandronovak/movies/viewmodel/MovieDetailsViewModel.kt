package me.leandronovak.movies.viewmodel

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
    val isLoading : MutableLiveData<Boolean> = MutableLiveData()

    fun getMovie(id: Int) {
        isLoading.value = true
        ApiService.movieService.getMovie(id).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                when {
                    response.isSuccessful -> {
                        response.body()?.let {movieResponse ->
                            val movie = movieResponse.getMovieModel()
                            movieLiveData.value = movie

                            isLoading.value = false
                        }
                    }
                    //TODO: Implement failure
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                //TODO: Implement
            }
        })
    }
}