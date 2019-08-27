package com.github.pedrodimoura.news.common.data.datasource.remote

import androidx.paging.PagedList

abstract class RemoteBoundary<Input, Output> : PagedList.BoundaryCallback<Output>() {

    protected var currentPage = 1
    protected var isExecutingTask = false
    abstract val params: Input?

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