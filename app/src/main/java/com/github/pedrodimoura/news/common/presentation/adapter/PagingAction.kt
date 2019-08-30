package com.github.pedrodimoura.news.common.presentation.adapter

sealed class PagingAction {
    data class FetchNext(val page: Int) : PagingAction()
    object Idle : PagingAction()
}