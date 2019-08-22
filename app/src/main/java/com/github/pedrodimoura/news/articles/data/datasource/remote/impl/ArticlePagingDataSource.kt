package com.github.pedrodimoura.news.articles.data.datasource.remote.impl

import androidx.paging.DataSource
import com.github.pedrodimoura.news.articles.domain.entity.Article

class ArticlePagingDataSource(
    private val articlePageKeyDataSource: ArticlePageKeyDataSource
) : DataSource.Factory<Int, Article>() {

    override fun create(): DataSource<Int, Article> = articlePageKeyDataSource
}