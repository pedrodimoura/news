package com.github.pedrodimoura.news.articles.data.datasource.remote.entity

import com.github.pedrodimoura.news.common.data.converter.NullToEmptyString

data class TopHeadlinesRemote(
    @NullToEmptyString
    val status: String = "",
    val totalResults: Int = -1,
    val articles: List<ArticleRemote> = emptyList()
)