package com.github.pedrodimoura.news.articles.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesResult
import com.github.pedrodimoura.news.articles.domain.usecase.ClearArticlesUseCase
import com.github.pedrodimoura.news.articles.domain.usecase.FetchTopHeadlinesUseCase
import com.github.pedrodimoura.news.articles.presentation.ArticleInteractor
import com.github.pedrodimoura.news.common.presentation.viewmodel.BaseViewModel
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState
import com.github.pedrodimoura.news.common.presentation.viewmodel.ThreadContextProvider

class ArticleViewModel(
    private val fetchTopHeadlinesUseCase: FetchTopHeadlinesUseCase,
    private val clearArticlesUseCase: ClearArticlesUseCase,
    threadContextProvider: ThreadContextProvider
) : BaseViewModel(threadContextProvider), ArticleInteractor.ViewModel {

    private val flowState = MutableLiveData<FlowState<TopHeadlinesResult>>()

    override fun fetch(country: String, pageSize: Int, invalidatingSource: Boolean) =
        executeFlow(flowState) {
            val r = fetchTopHeadlinesUseCase.execute(
                TopHeadlinesParams(
                    country,
                    pageSize = pageSize,
                    invalidatingSource = invalidatingSource
                )
            )
            r
        }

    override fun observeTopHeadlines(): LiveData<FlowState<TopHeadlinesResult>> =
        flowState

    override fun clearArticles() = executeNonFlow { clearArticlesUseCase.execute() }
}