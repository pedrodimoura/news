package com.github.pedrodimoura.news.articles.data.datasource.remote.impl

import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams
import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository
import com.github.pedrodimoura.news.common.data.datasource.remote.RemoteBoundary
import com.github.pedrodimoura.news.common.presentation.viewmodel.ThreadContextProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class ArticleRemoteBoundaryCallback(
    private val articleRepository: ArticleRepository,
    threadContextProvider: ThreadContextProvider
) : RemoteBoundary<TopHeadlinesParams, Article>(threadContextProvider) {

    override var params: TopHeadlinesParams? = null

    override fun fetchAndSave() {
        if (super.isRequesting())
            return

        coroutineScope.launch(threadContextProvider.ui) {

            try {
                super.start()

                if (params?.invalidatingSource!!) {
                    currentPage = 1
                    params?.invalidatingSource = false
                }

                params?.page = currentPage

                withContext(threadContextProvider.io) {
                    val articles = articleRepository.fetchTopHeadlines(params!!)
                    articleRepository.save(articles)
                    currentPage++
                }

                super.success()
            } catch (e: Exception) {
                super.failure(e)
            } finally {
                super.done()
            }

        }
    }

    override fun updateCurrentPageWithLastPageLoaded() {
        coroutineScope.launch(threadContextProvider.ui) {
            super.isRequesting()
            withContext(threadContextProvider.io) {
                val rowCount = articleRepository.count()
                val pagesAvailable = (rowCount.toFloat() / params?.pageSize!!).roundToInt()
                currentPage = pagesAvailable
                currentPage++
            }
            super.done()
        }
    }
}