package com.github.pedrodimoura.news

import android.app.Application
import com.github.pedrodimoura.news.articles.di.articleModule
import com.github.pedrodimoura.news.common.di.asyncModule
import com.github.pedrodimoura.news.common.di.localModule
import com.github.pedrodimoura.news.common.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class News : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(arrayListOf(asyncModule, localModule, networkModule, articleModule))
        }
    }

}