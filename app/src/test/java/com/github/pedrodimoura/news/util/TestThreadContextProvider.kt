package com.github.pedrodimoura.news.util

import com.github.pedrodimoura.news.common.presentation.viewmodel.ThreadContextProvider
import kotlinx.coroutines.Dispatchers

class TestThreadContextProvider : ThreadContextProvider() {
    override val io = Dispatchers.Unconfined
    override val ui = Dispatchers.Unconfined
}