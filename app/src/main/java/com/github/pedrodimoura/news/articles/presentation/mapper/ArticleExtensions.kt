package com.github.pedrodimoura.news.articles.presentation.mapper

import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.presentation.entity.ArticleView

fun Article.asArticleView() =
    ArticleView(title, description, content, publishedAt, urlToImage, url, source.name)