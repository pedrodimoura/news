package com.github.pedrodimoura.news.articles.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.github.pedrodimoura.news.articles.domain.entity.Article

interface ArticleRepository {

    suspend fun fetchTopHeadlines(): LiveData<PagedList<Article>>

}