package com.github.pedrodimoura.news.articles.data.datasource.mapper

import com.github.pedrodimoura.news.articles.data.datasource.remote.entity.SourceRemote
import com.github.pedrodimoura.news.articles.domain.entity.Source

fun SourceRemote.asSource() = Source(name, name)