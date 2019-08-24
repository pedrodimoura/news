package com.github.pedrodimoura.news.articles.data.datasource.remote.entity

import com.github.pedrodimoura.news.common.data.converter.NullToEmptyString
import java.util.Date

data class ArticleRemote(
    val sourceRemote: SourceRemote = SourceRemote(),
    @NullToEmptyString
    val author: String = "",
    @NullToEmptyString
    val title: String = "",
    @NullToEmptyString
    val description: String = "",
    @NullToEmptyString
    val url: String = "",
    @NullToEmptyString
    val urlToImage: String = "",
    val publishedAt: Date = Date(),
    @NullToEmptyString
    val content: String = ""
)