package me.leandronovak.movies.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.leandronovak.movies.data.ApiService
import me.leandronovak.movies.data.model.Movie
import me.leandronovak.movies.data.rest.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel : ViewModel() {
    val moviesLiveData: MutableLiveData<ArrayList<Movie>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    fun getMovies() {
        isLoading.value = true
        ApiService.movieService.getMoviesList().enqueue(object : Callback<List<MovieResponse>> {
            override fun onResponse(
                call: Call<List<MovieResponse>>,
                response: Response<List<MovieResponse>>
            ) {
                if (response.isSuccessful) {
                    val movies = ArrayList<Movie>()

                    response.body()?.let { movieListResponse ->
                        for (resultItem in movieListResponse) {
                            val movie = resultItem.getMovieModel()
                            movies.add(movie)
                        }

                        moviesLiveData.postValue(movies)
                        isLoading.value = false
                    }
                } else {
                    error.value = "error"
                    Log.e("GET MOVIES", response.code().toString())
                }
            }

            override fun onFailure(call: Call<List<MovieResponse>>, t: Throwable) {
                error.value = t.message
                Log.e("GET MOVIES", t.message ?: "")
            }
        })
    }
}