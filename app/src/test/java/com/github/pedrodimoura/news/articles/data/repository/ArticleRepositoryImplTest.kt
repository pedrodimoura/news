package com.github.pedrodimoura.news.articles.data.repository

import com.github.pedrodimoura.news.NetworkTest
import com.github.pedrodimoura.news.articles.data.datasource.remote.entity.TopHeadlinesRemote
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams
import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.test.inject
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

class ArticleRepositoryImplTest : NetworkTest() {

    private val articleRepository: ArticleRepository by inject()
    private val moshi: Moshi by inject()

    @Test
    fun `Should FetchArticles return a valid Article`() = runBlocking {
        mockFetchArticleOKResponse()
        val result = articleRepository.fetchTopHeadlines(topHeadlinesParams)
        assertEquals(topHeadlinesRemote.articles, result)
    }

    @Test(expected = SocketTimeoutException::class)
    fun `Should FetchArticles throws SocketTimeoutException when Client Timeout`() {
        runBlocking {
            mockFetchArticleClientTimeoutResponse()
            articleRepository.fetchTopHeadlines(topHeadlinesParams)
        }
    }

    @Test(expected = HttpException::class)
    fun `Should FetchArticles throws HttpException when Gateway Timeout`() {
        runBlocking {
            mockFetchArticleGatewayTimeoutResponse()
            articleRepository.fetchTopHeadlines(topHeadlinesParams)
        }
    }

    private fun mockFetchArticleOKResponse() {
        val mockResponse = MockResponse()
        val jsonAdapter = moshi.adapter(TopHeadlinesRemote::class.java)
        mockResponse.setResponseCode(HttpURLConnection.HTTP_OK)
        mockResponse.setBody(jsonAdapter.toJson(topHeadlinesRemote))
        mockWebServer.enqueue(mockResponse)
    }

    private fun mockFetchArticleClientTimeoutResponse() {
        val mockRemote = MockResponse()
        mockRemote.setResponseCode(HttpURLConnection.HTTP_CLIENT_TIMEOUT)
        mockWebServer.enqueue(mockRemote)
    }

    private fun mockFetchArticleGatewayTimeoutResponse() {
        val mockRemote = MockResponse()
        mockRemote.setResponseCode(HttpURLConnection.HTTP_GATEWAY_TIMEOUT)
        mockWebServer.enqueue(mockRemote)
    }

    companion object {
        @JvmStatic
        val topHeadlinesParams = TopHeadlinesParams("de", 1, 21)
        @JvmStatic
        val topHeadlinesRemote = TopHeadlinesRemote("ok", 34, arrayListOf())
    }

}