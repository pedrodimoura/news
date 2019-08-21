package com.github.pedrodimoura.news.common.presentation.viewmodel

sealed class FlowState<out T> {
    object Loading : FlowState<Nothing>()
    data class Success<T>(val data: T?) : FlowState<T>()
    data class Error(val throwable: Throwable) : FlowState<Nothing>()
    object Done : FlowState<Nothing>()
}