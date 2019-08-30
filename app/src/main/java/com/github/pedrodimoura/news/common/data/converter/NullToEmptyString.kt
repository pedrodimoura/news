package com.github.pedrodimoura.news.common.data.converter

import com.squareup.moshi.JsonQualifier

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullToEmptyString