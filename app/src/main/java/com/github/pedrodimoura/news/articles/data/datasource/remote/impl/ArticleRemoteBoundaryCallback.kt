package com.github.pedrodimoura.news.articles.data.datasource.remote.impl

import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams
import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository
import com.github.pedrodimoura.news.common.data.datasource.remote.RemoteBoundary
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import kotlin.math.roundToInt

class ArticleRemoteBoundaryCallback(
    private val articleRepository: ArticleRepository
) : RemoteBoundary<TopHeadlinesParams, Article>() {

    override val params: TopHeadlinesParams? = null

    override fun fetchAndSave() {
        if (isExecutingTask)
            return

        isExecutingTask = true
        runBlocking {
            try {
                params.page = currentPage
                val articles = articleRepository.fetchTopHeadlines(params)
                articleRepository.save(articles)
                currentPage++
                isExecutingTask = false
            } catch (e: Exception) {
                Timber.tag("boundaryCallback").d("$e")
            }
        }
    }

    override fun updateCurrentPageWithLastPageLoaded() {
        runBlocking {
            val rowCount = articleRepository.count()
            val pagesAvailable = (rowCount.toFloat() / params.pageSize).roundToInt()
            currentPage = pagesAvailable
            currentPage++
        }
    }
}