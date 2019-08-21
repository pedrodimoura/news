package com.github.pedrodimoura.news.articles.domain.repository

import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlines

interface TopHeadlinesRepository {

    suspend fun fetch(): TopHeadlines

}