package com.github.pedrodimoura.news.common.data.datasource.remote

sealed class NetworkCallState {
    object Requesting : NetworkCallState()
    object Success : NetworkCallState()
    data class Failed(val throwable: Throwable) : NetworkCallState()
    object Done : NetworkCallState()
}