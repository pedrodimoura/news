package com.github.pedrodimoura.news.articles.data.repository

import androidx.paging.DataSource
import com.github.pedrodimoura.news.articles.data.datasource.ArticleDataSource
import com.github.pedrodimoura.news.articles.data.datasource.local.entity.ArticleLocal
import com.github.pedrodimoura.news.articles.data.datasource.mapper.asArticle
import com.github.pedrodimoura.news.articles.data.datasource.mapper.asArticleLocal
import com.github.pedrodimoura.news.articles.data.datasource.remote.entity.ArticleRemote
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams
import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository

class ArticleRepositoryImpl(
    private val articleLocalDataSource: ArticleDataSource<ArticleLocal>,
    private val articleRemoteDataSource: ArticleDataSource<ArticleRemote>
) : ArticleRepository {

    override suspend fun fetchTopHeadlines(topHeadlinesParams: TopHeadlinesParams): List<Article> =
        articleRemoteDataSource.fetchMoreTopHeadlines(topHeadlinesParams).map { it.asArticle() }

    override suspend fun getAvailableTopHeadlines(): DataSource.Factory<Int, Article> =
        articleLocalDataSource.getAvailableTopHeadlines().map { it.asArticle() }

    override suspend fun save(articles: List<Article>) =
        articleLocalDataSource.save(articles.map { it.asArticleLocal() })

    override suspend fun count(): Int = articleLocalDataSource.count()

}