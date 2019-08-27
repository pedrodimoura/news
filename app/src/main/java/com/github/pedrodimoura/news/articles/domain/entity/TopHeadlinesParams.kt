package com.github.pedrodimoura.news.articles.domain.entity

data class TopHeadlinesParams(
    val country: String,
    var page: Int,
    val pageSize: Int
)