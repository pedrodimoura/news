package com.github.pedrodimoura.news.articles.data.datasource.mapper

import com.github.pedrodimoura.news.articles.data.datasource.local.entity.ArticleLocal
import com.github.pedrodimoura.news.articles.data.datasource.remote.entity.ArticleRemote
import com.github.pedrodimoura.news.articles.domain.entity.Article

fun ArticleRemote.asArticle() = Article(
    sourceRemote.asSource(),
    author,
    title,
    description,
    url,
    urlToImage,
    publishedAt,
    content
)

fun ArticleRemote.asArticleLocal() = ArticleLocal(
    sourceRemote.asSourceLocal(),
    author,
    title,
    description,
    url,
    urlToImage,
    publishedAt,
    content
)