package com.github.pedrodimoura.news.articles.data.datasource.remote.impl

import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams
import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository
import com.github.pedrodimoura.news.common.data.datasource.remote.RemoteBoundary
import com.github.pedrodimoura.news.common.presentation.viewmodel.ThreadContextProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import kotlin.math.roundToInt

class ArticleRemoteBoundaryCallback(
    private val articleRepository: ArticleRepository,
    threadContextProvider: ThreadContextProvider
) : RemoteBoundary<TopHeadlinesParams, Article>(threadContextProvider) {

    override var params: TopHeadlinesParams? = null

    override fun fetchAndSave() {
        if (isExecutingTask)
            return
        isExecutingTask = true
        coroutineScope.launch(threadContextProvider.io) {
            try {
                if (params?.invalidatingSource!!) {
                    currentPage = 1
                    params?.invalidatingSource = false
                }

                params?.page = currentPage
                val articles = articleRepository.fetchTopHeadlines(params!!)
                articleRepository.save(articles)
                currentPage++
                isExecutingTask = false
            } catch (e: Exception) {
                Timber.tag("boundaryCallback").d("$e")
            }
        }
    }

    override fun updateCurrentPageWithLastPageLoaded() {
        coroutineScope.launch(threadContextProvider.io) {
            val rowCount = articleRepository.count()
            val pagesAvailable = (rowCount.toFloat() / params?.pageSize!!).roundToInt()
            currentPage = pagesAvailable
            currentPage++
        }
    }
}