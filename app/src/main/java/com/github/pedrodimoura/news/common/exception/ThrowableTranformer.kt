package com.github.pedrodimoura.news.common.exception

interface ThrowableTranformer {

    suspend fun transform(incoming: Throwable): Throwable

}