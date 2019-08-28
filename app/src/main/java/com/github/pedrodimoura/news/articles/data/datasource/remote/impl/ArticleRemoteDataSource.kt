package com.github.pedrodimoura.news.articles.data.datasource.remote.impl

import androidx.paging.DataSource
import com.github.pedrodimoura.news.articles.data.datasource.ArticleDataSource
import com.github.pedrodimoura.news.articles.data.datasource.remote.ArticleService
import com.github.pedrodimoura.news.articles.data.datasource.remote.entity.ArticleRemote
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams

class ArticleRemoteDataSource(
    private val articleService: ArticleService
) : ArticleDataSource<ArticleRemote> {

    override suspend fun fetchMoreTopHeadlines(
        topHeadlinesParams: TopHeadlinesParams
    ): List<ArticleRemote> {
        val topHeadlines = articleService.fetchTopHeadlines(
            topHeadlinesParams.country,
            topHeadlinesParams.page,
            topHeadlinesParams.pageSize
        )
        return topHeadlines.articles
    }

    override suspend fun getAvailableTopHeadlines(): DataSource.Factory<Int, ArticleRemote> =
        throw UnsupportedOperationException()

    override suspend fun save(articles: List<ArticleRemote>) =
        throw UnsupportedOperationException()

    override suspend fun count(): Int =
        throw UnsupportedOperationException()

}