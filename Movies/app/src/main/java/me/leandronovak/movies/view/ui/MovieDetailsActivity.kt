package me.leandronovak.movies.view.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.toolbar.*
import me.leandronovak.movies.BR
import me.leandronovak.movies.R
import me.leandronovak.movies.databinding.ActivityMovieDetailsBinding
import me.leandronovak.movies.databinding.ActivityMoviesBinding
import me.leandronovak.movies.view.adapter.MoviesAdapter
import me.leandronovak.movies.view.ui.base.BaseActivity
import me.leandronovak.movies.viewmodel.MovieDetailsViewModel
import me.leandronovak.movies.viewmodel.MoviesViewModel

class MovieDetailsActivity : BaseActivity() {
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val id = intent.getIntExtra(ID, 0)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)
        binding.viewModel = movieDetailsViewModel
        binding.setLifecycleOwner { this.lifecycle }
        binding.executePendingBindings()

        setupToolbar(toolbarMain, id.toString(), true)

        movieDetailsViewModel.getMovie(id)
    }

    companion object {
        private const val ID = "ID"

        fun getIntent(context: Context, id: Int) : Intent {
            return Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(ID, id)
            }
        }
    }
}
