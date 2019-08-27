package com.github.pedrodimoura.news.articles.data.datasource

import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams
import com.github.pedrodimoura.news.common.domain.datasource.DataSource

interface ArticleDataSource<T> : DataSource {

    suspend fun fetchMoreTopHeadlines(topHeadlinesParams: TopHeadlinesParams): List<T>

    suspend fun getAvailableTopHeadlines(): androidx.paging.DataSource.Factory<Int, T>

    suspend fun save(articles: List<T>)

    suspend fun count(): Int

}