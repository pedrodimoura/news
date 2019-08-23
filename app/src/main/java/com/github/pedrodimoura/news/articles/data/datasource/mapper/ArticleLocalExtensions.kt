package com.github.pedrodimoura.news.articles.data.datasource.mapper

import com.github.pedrodimoura.news.articles.data.datasource.local.entity.ArticleLocal
import com.github.pedrodimoura.news.articles.domain.entity.Article

fun ArticleLocal.asArticle() = Article(
    sourceLocal.asSource(),
    author,
    title,
    description,
    url,
    urlToImage,
    publishedAt,
    content
)