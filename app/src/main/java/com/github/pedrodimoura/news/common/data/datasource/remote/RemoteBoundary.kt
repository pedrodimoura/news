package com.github.pedrodimoura.news.common.data.datasource.remote

import androidx.paging.PagedList

abstract class RemoteBoundary<Input, Output> : PagedList.BoundaryCallback<Output>() {

    // TODO: Add Coroutine Scope here

    protected var currentPage = 1
    protected var isExecutingTask = false
    abstract var params: Input?

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        fetchAndSave()
    }

    override fun onItemAtFrontLoaded(itemAtFront: Output) {
        super.onItemAtFrontLoaded(itemAtFront)
        updateCurrentPageWithLastPageLoaded()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Output) {
        super.onItemAtEndLoaded(itemAtEnd)
        fetchAndSave()
    }

    abstract fun fetchAndSave()

    abstract fun updateCurrentPageWithLastPageLoaded()

}