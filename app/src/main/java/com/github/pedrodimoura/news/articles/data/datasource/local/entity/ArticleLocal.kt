package com.github.pedrodimoura.news.articles.data.datasource.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import java.util.Date

@Entity(
    tableName = "article",
    primaryKeys = ["url", "publishedAt"]
)
data class ArticleLocal(
    @Embedded(prefix = "source")
    val sourceLocal: SourceLocal = SourceLocal(),
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val publishedAt: Date = Date(),
    val content: String = ""
)