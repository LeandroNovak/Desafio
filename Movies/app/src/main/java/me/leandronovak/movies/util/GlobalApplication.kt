package me.leandronovak.movies.util

import android.app.Application
import android.content.Context

// Classe criada para armazenar o contexto
class GlobalApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: GlobalApplication? = null

        fun getContext(): Context {
            return instance!!.applicationContext
        }
    }
}