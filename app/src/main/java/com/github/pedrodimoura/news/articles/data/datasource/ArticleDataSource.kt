package com.github.pedrodimoura.news.articles.data.datasource

import androidx.lifecycle.LiveData
import com.github.pedrodimoura.news.common.domain.datasource.DataSource

interface ArticleDataSource<T> : DataSource {

    suspend fun fetchTopHeadlines(
        country: String,
        page: Int,
        pageSize: Int
    ): LiveData<List<T>>

    suspend fun save(vararg article: T)

}