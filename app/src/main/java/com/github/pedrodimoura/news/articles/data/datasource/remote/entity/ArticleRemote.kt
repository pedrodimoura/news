package com.github.pedrodimoura.news.articles.data.datasource.remote.entity

import com.github.pedrodimoura.news.common.data.converter.NullToEmptyString
import com.squareup.moshi.Json
import java.util.Date

data class ArticleRemote(
    @Json(name = "source")
    val sourceRemote: SourceRemote = SourceRemote(),
    @NullToEmptyString
    @Json(name = "author")
    val author: String = "",
    @NullToEmptyString
    @Json(name = "title")
    val title: String = "",
    @NullToEmptyString
    @Json(name = "description")
    val description: String = "",
    @NullToEmptyString
    @Json(name = "url")
    val url: String = "",
    @NullToEmptyString
    @Json(name = "urlToImage")
    val urlToImage: String = "",
    @Json(name = "publishedAt")
    val publishedAt: Date = Date(),
    @NullToEmptyString
    @Json(name = "content")
    val content: String = ""
)