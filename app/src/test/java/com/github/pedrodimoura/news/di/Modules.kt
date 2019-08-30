package com.github.pedrodimoura.news.di

import com.github.pedrodimoura.news.common.presentation.viewmodel.ThreadContextProvider
import com.github.pedrodimoura.news.util.TestThreadContextProvider
import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module

val testModule = module {
    single<ThreadContextProvider>(override = true) { TestThreadContextProvider() }
}

val testModuleNetwork = module {
    single { MockWebServer() }
}