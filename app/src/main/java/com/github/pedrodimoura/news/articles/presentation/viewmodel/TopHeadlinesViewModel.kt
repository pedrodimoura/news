package com.github.pedrodimoura.news.articles.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlines
import com.github.pedrodimoura.news.articles.domain.uc.FetchTopHeadlinesUseCase
import com.github.pedrodimoura.news.articles.presentation.TopHeadlinesInteractor
import com.github.pedrodimoura.news.common.presentation.viewmodel.BaseViewModel
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState
import com.github.pedrodimoura.news.common.presentation.viewmodel.ThreadContextProvider

class TopHeadlinesViewModel(
    private val fetchTopHeadlinesUseCase: FetchTopHeadlinesUseCase,
    threadContextProvider: ThreadContextProvider
) :
    BaseViewModel(threadContextProvider), TopHeadlinesInteractor.ViewModel {

    private val flowState = MutableLiveData<FlowState<TopHeadlines>>()

    override suspend fun fetch() = executeFlow(flowState) {
        fetchTopHeadlinesUseCase.execute()
    }

    override suspend fun observeTopHeadlines(): LiveData<FlowState<TopHeadlines>> = flowState

}