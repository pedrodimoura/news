package com.github.pedrodimoura.news.articles.exception

import com.github.pedrodimoura.news.common.exception.ThrowableTranformer
import retrofit2.HttpException

object RemoteErrorTransformer : ThrowableTranformer {

    override suspend fun transform(incoming: Throwable): Throwable {
        return when (incoming) {
            is HttpException -> parseUsingStatusCode(incoming.code())
            else -> incoming
        }
    }

    private fun parseUsingStatusCode(code: Int) =
        when (code) {
            426 -> RemoteError.UpgradeRequired
            else -> RemoteError.UnexpectedResponse
        }
}