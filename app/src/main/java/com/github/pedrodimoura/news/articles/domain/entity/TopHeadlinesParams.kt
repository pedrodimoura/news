package com.github.pedrodimoura.news.articles.domain.entity

data class TopHeadlinesParams(
    val country: String,
    val page: Int,
    val pageSize: Int
)