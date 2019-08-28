package com.github.pedrodimoura.news.articles.data.datasource.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "article",
    indices = [
        Index(
            value = ["url", "publishedAt"],
            name = "url_published_index",
            unique = true
        )]
)
data class ArticleLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
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