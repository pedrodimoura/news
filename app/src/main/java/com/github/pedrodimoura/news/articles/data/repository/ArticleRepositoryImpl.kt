package com.github.pedrodimoura.news.articles.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.github.pedrodimoura.news.articles.data.datasource.ArticleDataSource
import com.github.pedrodimoura.news.articles.data.datasource.local.entity.ArticleLocal
import com.github.pedrodimoura.news.articles.data.datasource.mapper.asArticle
import com.github.pedrodimoura.news.articles.data.datasource.mapper.asArticleLocal
import com.github.pedrodimoura.news.articles.data.datasource.remote.entity.ArticleRemote
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams
import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository
import kotlinx.coroutines.runBlocking

class ArticleRepositoryImpl(
    private val articleLocalDataSource: ArticleDataSource<ArticleLocal>,
    private val articleRemoteDataSource: ArticleDataSource<ArticleRemote>
) : ArticleRepository {

    override suspend fun fetchTopHeadlines(
        topHeadlinesParams: TopHeadlinesParams
    ): LiveData<List<Article>> =
        Transformations.map(articleRemoteDataSource.fetchTopHeadlines(topHeadlinesParams)) {
            it.map { articleRemote ->
                runBlocking { saveArticle(articleRemote.asArticleLocal()) }
                articleRemote.asArticle()
            }
        }

    override suspend fun getAvailableTopHeadlines(): LiveData<List<Article>> =
        Transformations.map(articleLocalDataSource.getAvailableTopHeadlines()) {
            it.map { articleLocal -> articleLocal.asArticle() }
        }

    private suspend fun saveArticle(articleLocal: ArticleLocal) =
        articleLocalDataSource.save(articleLocal)
}