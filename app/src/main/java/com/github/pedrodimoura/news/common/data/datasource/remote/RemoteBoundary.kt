package com.github.pedrodimoura.news.common.data.datasource.remote

import androidx.paging.PagedList
import com.github.pedrodimoura.news.common.presentation.viewmodel.ThreadContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

abstract class RemoteBoundary<Input, Output>(protected val threadContextProvider: ThreadContextProvider) :
    PagedList.BoundaryCallback<Output>() {

    private val job: Job by lazy { Job() }
    protected val coroutineScope: CoroutineScope by lazy {
        CoroutineScope(threadContextProvider.io + job)
    }

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