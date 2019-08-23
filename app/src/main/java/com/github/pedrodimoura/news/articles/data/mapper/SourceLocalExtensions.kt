package com.github.pedrodimoura.news.articles.data.mapper

import com.github.pedrodimoura.news.articles.data.datasource.local.entity.SourceLocal
import com.github.pedrodimoura.news.articles.domain.entity.Source

fun SourceLocal.toSource() = Source(name, name)