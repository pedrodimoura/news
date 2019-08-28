package com.github.pedrodimoura.news.articles.domain.repository

import androidx.paging.DataSource
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams

interface ArticleRepository {

    suspend fun fetchTopHeadlines(topHeadlinesParams: TopHeadlinesParams): List<Article>

    suspend fun getAvailableTopHeadlines(): DataSource.Factory<Int, Article>

    suspend fun save(articles: List<Article>)

    suspend fun count(): Int

    suspend fun clearArticles()

}