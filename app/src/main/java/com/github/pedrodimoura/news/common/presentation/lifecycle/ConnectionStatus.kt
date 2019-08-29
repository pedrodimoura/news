package com.github.pedrodimoura.news.common.presentation.lifecycle

sealed class ConnectionStatus {
    object Connected : ConnectionStatus()
    object Disconnected : ConnectionStatus()
}