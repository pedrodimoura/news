package com.github.pedrodimoura.news.articles.data.datasource.mapper

import com.github.pedrodimoura.news.articles.data.datasource.local.entity.ArticleLocal
import com.github.pedrodimoura.news.articles.domain.entity.Article

fun Article.asArticleLocal() = ArticleLocal(
    source.asSourceLocal(),
    author,
    title,
    description,
    url,
    urlToImage,
    publishedAt,
    content
)