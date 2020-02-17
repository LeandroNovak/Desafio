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
    val relatedMovies: MutableLiveData<ArrayList<Movie>> = MutableLiveData()
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

    fun getRelatedMovies() {
        isLoading.value = true
        ApiService.movieService.getMoviesList().enqueue(object : Callback<List<MovieResponse>> {
            override fun onResponse(
                call: Call<List<MovieResponse>>,
                response: Response<List<MovieResponse>>
            ) {
                if (response.isSuccessful) {
                    val movies = ArrayList<Movie>()
                    val genres = movieLiveData.value?.genres ?: emptyList()
                    val id = movieLiveData?.value?.id ?: 0

                    response.body()?.let { movieListResponse ->
                        for (resultItem in movieListResponse) {
                            val movie = resultItem.getMovieModel()

                            if (movie.id != id && movie.genres.intersect(genres).count() >= 2) {
                                movies.add(movie)
                            }
                        }

                        relatedMovies.postValue(movies)
                        isLoading.value = false
                    }
                } else {
                    error.value = "error"
                    Log.e("GET RELATED MOVIES", response.code().toString())
                }
            }

            override fun onFailure(call: Call<List<MovieResponse>>, t: Throwable) {
                error.value = t.message
                Log.e("GET RELATED MOVIES", t.message ?: "")
            }
        })
    }
}