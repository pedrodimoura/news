package com.github.pedrodimoura.news.articles.data.datasource

import androidx.lifecycle.LiveData
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams
import com.github.pedrodimoura.news.common.domain.datasource.DataSource

interface ArticleDataSource<T> : DataSource {

    suspend fun fetchTopHeadlines(topHeadlinesParams: TopHeadlinesParams): LiveData<List<T>>

    suspend fun getAvailableTopHeadlines(): LiveData<List<T>>

    suspend fun save(article: T)

}