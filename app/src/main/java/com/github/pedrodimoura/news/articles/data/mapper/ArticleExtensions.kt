package com.github.pedrodimoura.news.articles.data.mapper

import com.github.pedrodimoura.news.articles.data.datasource.local.entity.ArticleLocal
import com.github.pedrodimoura.news.articles.domain.entity.Article

fun Article.toArticleLocal() = ArticleLocal(source.toSourceLocal(), author, title, description, url, urlToImage, publishedAt, content)