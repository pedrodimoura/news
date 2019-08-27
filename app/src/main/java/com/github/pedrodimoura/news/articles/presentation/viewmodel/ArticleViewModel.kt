package com.github.pedrodimoura.news.articles.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.usecase.FetchTopHeadlinesUseCase
import com.github.pedrodimoura.news.articles.presentation.ArticleInteractor
import com.github.pedrodimoura.news.common.presentation.viewmodel.BaseViewModel
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState
import com.github.pedrodimoura.news.common.presentation.viewmodel.ThreadContextProvider

class ArticleViewModel(
    private val fetchTopHeadlinesUseCase: FetchTopHeadlinesUseCase,
    threadContextProvider: ThreadContextProvider
) : BaseViewModel(threadContextProvider), ArticleInteractor.ViewModel {

    private val flowState = MutableLiveData<FlowState<LiveData<PagedList<Article>>>>()
    val flowStateNothing = MutableLiveData<FlowState<Int>>()

    override fun fetch(page: Int) = executeFlow(flowState) {
        fetchTopHeadlinesUseCase.execute()
    }

    override fun observeTopHeadlines(): LiveData<FlowState<LiveData<PagedList<Article>>>> =
        flowState
}