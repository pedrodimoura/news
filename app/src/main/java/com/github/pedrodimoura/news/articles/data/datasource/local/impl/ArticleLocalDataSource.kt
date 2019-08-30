package com.github.pedrodimoura.news.articles.data.datasource.local.impl

import androidx.paging.DataSource
import com.github.pedrodimoura.news.articles.data.datasource.ArticleDataSource
import com.github.pedrodimoura.news.articles.data.datasource.local.ArticleDAO
import com.github.pedrodimoura.news.articles.data.datasource.local.entity.ArticleLocal
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams

class ArticleLocalDataSource(
    private val articleDAO: ArticleDAO
) : ArticleDataSource<ArticleLocal> {

    override suspend fun fetchMoreTopHeadlines(
        topHeadlinesParams: TopHeadlinesParams
    ): List<ArticleLocal> =
        throw UnsupportedOperationException()

    override suspend fun getAvailableTopHeadlines(): DataSource.Factory<Int, ArticleLocal> =
        articleDAO.queryAll()

    override suspend fun save(articles: List<ArticleLocal>) = articleDAO.save(articles)

    override suspend fun count(): Int = articleDAO.count()

    override suspend fun deleteAll() = articleDAO.deleteAll()

}