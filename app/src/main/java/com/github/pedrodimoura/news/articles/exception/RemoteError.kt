package com.github.pedrodimoura.news.articles.exception

sealed class RemoteError : Throwable() {
    object UnexpectedResponse : RemoteError()
    object UpgradeRequired : RemoteError()

    override fun toString(): String {
        return when (this) {
            is UnexpectedResponse -> "Unexpected error"
            is UpgradeRequired -> "Upgrade Required"
        }
    }
}