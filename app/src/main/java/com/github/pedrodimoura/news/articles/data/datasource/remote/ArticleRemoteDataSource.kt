package com.github.pedrodimoura.news.articles.data.datasource.remote

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.common.domain.datasource.DataSource

interface ArticleRemoteDataSource : DataSource {

    fun getArticlePagedList(): LiveData<PagedList<Article>>

}