package com.github.pedrodimoura.news.articles.data.datasource.remote.entity

import com.github.pedrodimoura.news.common.data.converter.NullToEmptyString
import com.squareup.moshi.Json

data class SourceRemote(
    @NullToEmptyString
    @Json(name = "name")
    val name: String = ""
)