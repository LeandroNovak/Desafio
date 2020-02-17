package me.leandronovak.movies.data

import me.leandronovak.movies.util.GlobalApplication
import me.leandronovak.movies.util.NetworkUtils
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiService {
    // Inicializa o retrofit habilitando cache
    private fun initRetrofit(): Retrofit {
        val context = GlobalApplication.getContext()

        val cacheSize = (5 * 1024 * 1024).toLong()
        val cache = Cache(context.cacheDir, cacheSize)

        val okHttpClient = OkHttpClient.Builder().cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (NetworkUtils.isInternetAvailable(context))
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, max-age=" + 5
                    ).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://desafio-mobile.nyc3.digitaloceanspaces.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val movieService: MovieService = initRetrofit().create(MovieService::class.java)
}