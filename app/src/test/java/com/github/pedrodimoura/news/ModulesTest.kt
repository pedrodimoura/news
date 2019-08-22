package com.github.pedrodimoura.news

import android.app.Application
import com.github.pedrodimoura.news.articles.di.articleModule
import com.github.pedrodimoura.news.common.di.networkModule
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.check.checkModules
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ModulesTest {

    @Mock lateinit var context: Application

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Should modules load with success`() {

        startKoin {
            androidContext(context)
            modules(arrayListOf(networkModule, articleModule))
        }.checkModules()
    }
}
