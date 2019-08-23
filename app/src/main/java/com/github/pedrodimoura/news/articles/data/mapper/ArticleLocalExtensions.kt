package com.github.pedrodimoura.news.articles.data.mapper

import com.github.pedrodimoura.news.articles.data.datasource.local.entity.ArticleLocal
import com.github.pedrodimoura.news.articles.data.datasource.mapper.asSource
import com.github.pedrodimoura.news.articles.domain.entity.Article

fun ArticleLocal.toArticle() = Article(
    sourceLocal.asSource(),
    author,
    title,
    description,
    url,
    urlToImage,
    publishedAt,
    content
)