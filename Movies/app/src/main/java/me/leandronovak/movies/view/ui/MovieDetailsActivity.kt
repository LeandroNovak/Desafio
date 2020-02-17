package me.leandronovak.movies.view.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
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
    private var id = 0
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        id = intent.getIntExtra(ID, 0)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)
        binding.viewModel = movieDetailsViewModel
        binding.setLifecycleOwner { this.lifecycle }
        binding.executePendingBindings()

        setupToolbar(toolbarMain, showHomeUp = true)

        movieDetailsViewModel.error.observe(this, Observer {
            it?.let { _ ->
                showAlertAndRetry()
            }
        })

        movieDetailsViewModel.getMovie(id)
    }

    private fun showAlertAndRetry() {
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it, R.style.AlertDialogTheme)
            builder.apply {
                setTitle("Error")
                setMessage("An error occurred while loading movies, check your connection and retry.")
                setPositiveButton(R.string.retry) { _, _ ->
                    movieDetailsViewModel.getMovie(id)
                }
            }
            builder.create()
        }

        alertDialog?.show()
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
