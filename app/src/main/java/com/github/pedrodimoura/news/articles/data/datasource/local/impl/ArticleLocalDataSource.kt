package com.github.pedrodimoura.news.articles.data.datasource.local.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.github.pedrodimoura.news.articles.data.datasource.ArticleDataSource
import com.github.pedrodimoura.news.articles.data.datasource.local.ArticleDAO
import com.github.pedrodimoura.news.articles.data.datasource.local.entity.ArticleLocal
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams

class ArticleLocalDataSource(
    private val articleDAO: ArticleDAO
) : ArticleDataSource<ArticleLocal> {

    override suspend fun fetchTopHeadlines(topHeadlinesParams: TopHeadlinesParams): LiveData<List<ArticleLocal>> =
        throw UnsupportedOperationException()

    override suspend fun getAvailableTopHeadlines(): LiveData<List<ArticleLocal>> =
        Transformations.map(articleDAO.queryAll()) { articles ->
            articles.map { article -> article }
        }

    override suspend fun save(article: ArticleLocal) = articleDAO.save(article)
}