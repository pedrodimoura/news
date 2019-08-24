package com.github.pedrodimoura.news.articles.data.datasource.remote.entity

import com.github.pedrodimoura.news.common.data.converter.NullToEmptyString

data class SourceRemote(@NullToEmptyString val name: String = "")