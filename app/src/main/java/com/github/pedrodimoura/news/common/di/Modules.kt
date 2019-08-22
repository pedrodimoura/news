package com.github.pedrodimoura.news.common.di

import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single { Retrofit.Builder().baseUrl("https://newsapi.org/v2/").build() }
}