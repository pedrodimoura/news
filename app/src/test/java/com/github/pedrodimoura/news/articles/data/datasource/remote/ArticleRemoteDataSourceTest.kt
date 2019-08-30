package com.github.pedrodimoura.news.articles.data.datasource.remote

import com.github.pedrodimoura.news.NetworkTest
import com.github.pedrodimoura.news.articles.data.datasource.remote.entity.TopHeadlinesRemote
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import org.koin.test.inject
import java.net.HttpURLConnection

class ArticleRemoteDataSourceTest : NetworkTest() {

    private val articleService: ArticleService by inject()

    private val moshi: Moshi by inject()

    @Test
    fun `Should `() = runBlocking {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(HttpURLConnection.HTTP_OK)
        val jsonAdapter = moshi.adapter(TopHeadlinesRemote::class.java)
        mockResponse.setBody(jsonAdapter.toJson(TopHeadlinesRemote()))

        mockWebServer.enqueue(mockResponse)

        val result =articleService.fetchTopHeadlines("de", 1, 21)

    }

}