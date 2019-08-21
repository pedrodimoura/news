package com.github.pedrodimoura.news.articles.domain.entity

data class TopHeadlines(
    val status: String = "",
    val totalResults: Int = -1,
    val articles: List<Article> = emptyList()
)