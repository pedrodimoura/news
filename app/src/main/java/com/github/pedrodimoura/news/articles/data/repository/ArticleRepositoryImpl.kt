package com.github.pedrodimoura.news.articles.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.github.pedrodimoura.news.articles.data.datasource.remote.ArticleRemoteDataSource
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository

class ArticleRepositoryImpl(
    private val articleRemoteDataSource: ArticleRemoteDataSource
) : ArticleRepository {

    override suspend fun fetchTopHeadlines(): LiveData<PagedList<Article>> =
        articleRemoteDataSource.getArticlePagedList()
}