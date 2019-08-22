package com.github.pedrodimoura.news.articles.data.datasource.remote.impl

import androidx.paging.PageKeyedDataSource
import com.github.pedrodimoura.news.articles.data.datasource.remote.ArticleService
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.common.presentation.viewmodel.ThreadContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ArticlePageKeyDataSource(
    private val articleService: ArticleService,
    private val coroutineScope: CoroutineScope,
    private val threadContextProvider: ThreadContextProvider
) : PageKeyedDataSource<Int, Article>() {

    companion object {
        private const val COUNTRY = "de"
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Article>) {
        coroutineScope.launch(threadContextProvider.io) {
            val result = articleService.fetchTopHeadlines(COUNTRY, 1, params.requestedLoadSize)
            callback.onResult(result.articles, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        coroutineScope.launch(threadContextProvider.io) {
            val result = articleService.fetchTopHeadlines(COUNTRY, params.key, params.requestedLoadSize)
            callback.onResult(result.articles, params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {}
}