package me.leandronovak.movies.view.ui.base

import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity

// Activity base para configuração da toolbar
open class BaseActivity : AppCompatActivity() {
    protected fun setupToolbar(toolbar: Toolbar, title: String = "", showHomeUp: Boolean = false) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(showHomeUp)
    }
}