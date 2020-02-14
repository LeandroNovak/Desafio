package me.leandronovak.movies.view.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_movies.view.*
import kotlinx.android.synthetic.main.toolbar.*
import me.leandronovak.movies.R
import me.leandronovak.movies.data.model.Movie
import me.leandronovak.movies.databinding.ActivityMoviesBinding
import me.leandronovak.movies.view.adapter.MoviesAdapter
import me.leandronovak.movies.view.ui.base.BaseActivity
import me.leandronovak.movies.viewmodel.MoviesViewModel

class MoviesActivity : BaseActivity() {
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var moviesAdapter: MoviesAdapter
    lateinit var binding: ActivityMoviesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_movies)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies)
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        binding.viewModel = moviesViewModel

        setupToolbar(toolbarMain, getString(R.string.movies_title))
        setRecyclerView()

        moviesViewModel.moviesLiveData.observe(this, Observer {
            it?.let { movies ->
                moviesAdapter.setList(movies)
            }
        })

        moviesViewModel.getMovies()
    }

    private fun setRecyclerView() {
        moviesAdapter = MoviesAdapter(emptyList()) { movie ->
            val intent = MovieDetailsActivity
                .getIntent(this@MoviesActivity, movie.id)
            this@MoviesActivity.startActivity(intent)
        }

        with(binding.root.recycler_movies) {
            layoutManager = GridLayoutManager(this@MoviesActivity, 3)
            adapter = moviesAdapter
            setHasFixedSize(true)
        }
    }
}
