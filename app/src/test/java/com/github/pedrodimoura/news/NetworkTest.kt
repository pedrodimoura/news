package com.github.pedrodimoura.news

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkRequest
import com.github.pedrodimoura.news.articles.di.articleModule
import com.github.pedrodimoura.news.articles.presentation.ui.MainActivity
import com.github.pedrodimoura.news.common.di.PROPERTY_KEY_BASE_URL
import com.github.pedrodimoura.news.common.di.localModule
import com.github.pedrodimoura.news.common.di.networkModule
import com.github.pedrodimoura.news.di.testModule
import com.github.pedrodimoura.news.di.testModuleNetwork
import okhttp3.mockwebserver.MockWebServer
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.File

abstract class NetworkTest : BaseTest() {

    @Mock
    private lateinit var context: Application

    val mockWebServer: MockWebServer by inject()

    override fun setup() {
        MockitoAnnotations.initMocks(this)

        startKoin {
            androidContext(context)
            modules(
                arrayListOf(
                    testModule,
                    testModuleNetwork,
                    localModule,
                    networkModule,
                    articleModule
                )
            )
        }

        getKoin().getOrCreateScope("MainActivity", qualifier = named<MainActivity>())

        declareMock<ConnectivityManager>()
        declareMock<NetworkRequest>()

        BDDMockito.`when`(context.cacheDir).thenReturn(File("cacheDir/"))

        mockWebServer.start(port = 0)
        getKoin().setProperty(PROPERTY_KEY_BASE_URL, mockWebServer.url("/news/").toString())
    }

    override fun tearDown() {
        mockWebServer.shutdown()
        stopKoin()
    }
}