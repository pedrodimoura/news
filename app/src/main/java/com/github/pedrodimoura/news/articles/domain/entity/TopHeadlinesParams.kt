package com.github.pedrodimoura.news.articles.domain.entity

data class TopHeadlinesParams(
    val country: String,
    var page: Int = 0,
    val pageSize: Int = 0
)