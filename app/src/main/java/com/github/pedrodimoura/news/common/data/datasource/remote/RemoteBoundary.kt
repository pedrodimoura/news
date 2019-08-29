package com.github.pedrodimoura.news.common.data.datasource.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val networkCallStateLiveData = MutableLiveData<NetworkCallState>()

    protected var currentPage = 1
    private var isExecutingTask = false
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

    fun start() {
        isExecutingTask = true
        networkCallStateLiveData.value = NetworkCallState.Requesting
    }

    fun isRequesting(): Boolean = isExecutingTask

    fun success() {
        networkCallStateLiveData.value = NetworkCallState.Success
    }

    fun failure(throwable: Throwable) {
        networkCallStateLiveData.value = NetworkCallState.Failed(throwable)
    }

    fun done() {
        isExecutingTask = false
        networkCallStateLiveData.value = NetworkCallState.Done
    }

    fun observeNetworkState(): LiveData<NetworkCallState> = networkCallStateLiveData

}