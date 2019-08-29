package com.github.pedrodimoura.news

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkRequest
import com.github.pedrodimoura.news.articles.di.articleModule
import com.github.pedrodimoura.news.articles.presentation.adapter.ArticleItemDecoration
import com.github.pedrodimoura.news.articles.presentation.adapter.ArticleSpanSizeLookup
import com.github.pedrodimoura.news.articles.presentation.ui.MainActivity
import com.github.pedrodimoura.news.common.di.asyncModule
import com.github.pedrodimoura.news.common.di.localModule
import com.github.pedrodimoura.news.common.di.networkModule
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ModulesTest : KoinTest {

    @Mock
    lateinit var context: Application

    private lateinit var koin: KoinApplication

    private val appModules = arrayListOf(
        asyncModule,
        localModule,
        networkModule,
        articleModule
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        koin = startKoin {
            androidContext(context)
            modules(appModules)
        }

        getKoin().getOrCreateScope("MainActivity", qualifier = named<MainActivity>())

        declareMock<ConnectivityManager>()
        declareMock<NetworkRequest>()

        BDDMockito.`when`(context.cacheDir).thenReturn(File("cacheDir/"))
    }

    @After
    fun tearDown() = stopKoin()

    @Test
    fun `Should modules load with success`() = koin.checkModules {
        create<ArticleItemDecoration> { parametersOf(2, 10) }
        create<ArticleSpanSizeLookup> { parametersOf(10) }
    }
}
