package com.github.pedrodimoura.news.articles.presentation

import androidx.lifecycle.LiveData
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlines
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState

interface TopHeadlinesInteractor {

    interface View {
        suspend fun fetch()
    }

    interface ViewModel {
        suspend fun fetch()
        suspend fun observeTopHeadlines(): LiveData<FlowState<TopHeadlines>>
    }

}