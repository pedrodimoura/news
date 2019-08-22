package com.github.pedrodimoura.news.articles.presentation

import androidx.lifecycle.LiveData
import com.github.pedrodimoura.news.articles.domain.usecase.ArticlePagedList
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState

interface ArticleInteractor {

    interface View {
        suspend fun fetch()
    }

    interface ViewModel {
        suspend fun fetch()
        suspend fun observeTopHeadlines(): LiveData<FlowState<ArticlePagedList>>
    }

}