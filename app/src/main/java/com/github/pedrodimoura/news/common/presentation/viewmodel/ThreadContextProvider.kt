package com.github.pedrodimoura.news.common.presentation.viewmodel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

open class ThreadContextProvider {
    open val io: CoroutineDispatcher = Dispatchers.IO
    open val ui: CoroutineDispatcher = Dispatchers.Main
}