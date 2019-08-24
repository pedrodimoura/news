package com.github.pedrodimoura.news.articles.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams
import com.github.pedrodimoura.news.articles.domain.usecase.ArticleTopHeadlines
import com.github.pedrodimoura.news.articles.domain.usecase.FetchTopHeadlinesUseCase
import com.github.pedrodimoura.news.articles.presentation.ArticleInteractor
import com.github.pedrodimoura.news.common.presentation.viewmodel.BaseViewModel
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState
import com.github.pedrodimoura.news.common.presentation.viewmodel.ThreadContextProvider

class ArticleViewModel(
    private val fetchTopHeadlinesUseCase: FetchTopHeadlinesUseCase,
    threadContextProvider: ThreadContextProvider
) : BaseViewModel(threadContextProvider), ArticleInteractor.ViewModel {

    private val flowState = MutableLiveData<FlowState<ArticleTopHeadlines>>()

    override fun fetch() = executeFlow(flowState) {
        val topHeadlinesParams = TopHeadlinesParams("de", 1, 21)
        fetchTopHeadlinesUseCase.execute(topHeadlinesParams)
    }

    override fun observeTopHeadlines(): LiveData<FlowState<ArticleTopHeadlines>> = flowState
}