package me.leandronovak.movies.view.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.toolbar.*
import me.leandronovak.movies.R
import me.leandronovak.movies.view.adapter.MoviesAdapter
import me.leandronovak.movies.view.ui.base.BaseActivity
import me.leandronovak.movies.viewmodel.MoviesViewModel

class MoviesActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        setupToolbar(toolbarMain, getString(R.string.movies_title))

        val viewModel: MoviesViewModel = ViewModelProviders.of(this)
            .get(MoviesViewModel::class.java)

        viewModel.moviesLiveData.observe(this, Observer {
            it?.let {movies ->
                with(recyclerMovies) {
                    layoutManager = GridLayoutManager(
                        this@MoviesActivity, 3
                    )

                    setHasFixedSize(true)
                    adapter = MoviesAdapter(movies) {movie ->
                        val intent = MovieDetailsActivity
                            .getIntent(this@MoviesActivity, movie.id)
                        this@MoviesActivity.startActivity(intent)
                    }
                }
            }
        })

        viewModel.getMovies()
    }
}
