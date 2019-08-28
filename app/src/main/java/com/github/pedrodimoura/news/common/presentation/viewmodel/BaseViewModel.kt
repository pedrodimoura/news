package com.github.pedrodimoura.news.common.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel(protected val threadContextProvider: ThreadContextProvider) :
    ViewModel() {

    private val job: Job by lazy { Job() }
    protected val coroutineScope: CoroutineScope by lazy { CoroutineScope(threadContextProvider.io + job) }

    protected inline fun launchUI(crossinline execution: suspend CoroutineScope.() -> Unit) {
        coroutineScope.launch(threadContextProvider.ui) { execution() }
    }

    protected suspend inline fun <reified T> withContextIO(crossinline execution: suspend CoroutineScope.() -> T): T {
        return withContext(threadContextProvider.io) { execution() }
    }

    protected inline fun <T> executeFlow(
        liveData: MutableLiveData<FlowState<T>>,
        crossinline execution: suspend CoroutineScope.() -> FlowState<T>
    ) {
        launchUI {
            with(liveData) {
                value = FlowState.Loading
                val result = withContextIO { execution() }
                value = result
                value = FlowState.Done
            }
        }
    }

    protected inline fun executeNonFlow(crossinline execution: suspend CoroutineScope.() -> Unit) {
        launchUI {
            withContextIO { execution() }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}