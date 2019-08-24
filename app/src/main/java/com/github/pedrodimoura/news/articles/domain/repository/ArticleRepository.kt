package com.github.pedrodimoura.news.articles.domain.repository

import androidx.lifecycle.LiveData
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams

interface ArticleRepository {

    suspend fun fetchTopHeadlines(topHeadlinesParams: TopHeadlinesParams): LiveData<List<Article>>

    suspend fun getAvailableTopHeadlines(): LiveData<List<Article>>

}