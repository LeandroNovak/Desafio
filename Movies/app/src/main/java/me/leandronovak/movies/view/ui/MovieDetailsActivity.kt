package me.leandronovak.movies.view.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.toolbar.*
import me.leandronovak.movies.R
import me.leandronovak.movies.view.ui.base.BaseActivity
import me.leandronovak.movies.viewmodel.MovieDetailsViewModel

class MovieDetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val id = intent.getIntExtra(ID, 0)
        setupToolbar(toolbarMain, id.toString(), true)

        val viewModel: MovieDetailsViewModel = ViewModelProviders.of(this)
            .get(MovieDetailsViewModel::class.java)

        viewModel.getMovie(id)
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
