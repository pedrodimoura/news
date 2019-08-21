package com.github.pedrodimoura.news.articles.domain.entity

import java.util.Date

data class Article(
    val source: Source = Source(),
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val publishedAt: Date = Date(),
    val content: String = ""
)