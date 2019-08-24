package com.github.pedrodimoura.news.common.di

import com.squareup.moshi.Moshi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

val networkModule = module {
    single {
        Moshi.Builder()
            .build()
    }
    single { MoshiConverterFactory.create(get()) }
    single {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(get())
            .build()
    }
}