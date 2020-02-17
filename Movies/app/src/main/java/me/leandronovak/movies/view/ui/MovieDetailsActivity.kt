package me.leandronovak.movies.view.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.toolbar.*
import me.leandronovak.movies.R
import me.leandronovak.movies.databinding.ActivityMovieDetailsBinding
import me.leandronovak.movies.view.adapter.GenresAdapter
import me.leandronovak.movies.view.adapter.RelatedMoviesAdapter
import me.leandronovak.movies.view.ui.base.BaseActivity
import me.leandronovak.movies.viewmodel.MovieDetailsViewModel

class MovieDetailsActivity : BaseActivity() {
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var relatedMoviesAdapter: RelatedMoviesAdapter
    private lateinit var genresAdapter: GenresAdapter
    lateinit var binding: ActivityMovieDetailsBinding
    private var id = 0

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
        setGenresRecyclerView()
        setRelatedMoviesRecyclerView()

        movieDetailsViewModel.movieLiveData.observe(this, Observer {
            it?.let { movie ->
                genresAdapter.setList(movie.genres)
                movieDetailsViewModel.getRelatedMovies()
            }
        })

        movieDetailsViewModel.relatedMovies.observe(this, Observer {
            it?.let { movies ->
                relatedMoviesAdapter.setList(movies)
            }
        })

        movieDetailsViewModel.error.observe(this, Observer {
            it?.let { _ ->
                showAlertAndRetry()
            }
        })

        movieDetailsViewModel.getMovie(id)
    }

    private fun setGenresRecyclerView() {
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        genres_recycler_view.layoutManager = layoutManager
        genresAdapter = GenresAdapter(emptyList())
        genres_recycler_view.adapter = genresAdapter
    }

    private fun setRelatedMoviesRecyclerView() {
        relatedMoviesAdapter = RelatedMoviesAdapter(emptyList()) { movie ->
            movieDetailsViewModel.getMovie(movie.id)
        }

        recycler_movies.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL,
            false
        )

        recycler_movies.adapter = relatedMoviesAdapter
        recycler_movies.setHasFixedSize(true)
    }

    private fun showAlertAndRetry() {
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it, R.style.AlertDialogTheme)
            builder.apply {
                setTitle(R.string.error)
                setMessage(R.string.error_movie_details)
                setPositiveButton(R.string.retry) { _, _ ->
                    movieDetailsViewModel.getMovie(id)
                }
            }
            builder.create()
        }

        alertDialog?.show()
    }

    /**
     * Auxiliar que retorna um intent com o parâmetro ID
     */
    companion object {
        private const val ID = "ID"

        fun getIntent(context: Context, id: Int): Intent {
            return Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(ID, id)
            }
        }
    }
}
