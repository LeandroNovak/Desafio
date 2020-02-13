package me.leandronovak.movies.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiService {
    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://desafio-mobile.nyc3.digitaloceanspaces.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val movieService: MovieService = initRetrofit().create(MovieService::class.java)
}