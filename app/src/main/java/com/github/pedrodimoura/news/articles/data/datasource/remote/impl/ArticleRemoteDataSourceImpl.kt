package com.github.pedrodimoura.news.articles.data.datasource.remote.impl

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.github.pedrodimoura.news.articles.data.datasource.remote.ArticleRemoteDataSource
import com.github.pedrodimoura.news.articles.domain.entity.Article

class ArticleRemoteDataSourceImpl(
    private val articlePagingDataSource: ArticlePagingDataSource
) : ArticleRemoteDataSource {

    override fun getArticlePagedList(): LiveData<PagedList<Article>> =
        LivePagedListBuilder(articlePagingDataSource, PagedList.Config.Builder().build()).build()
}

