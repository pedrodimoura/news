package com.github.pedrodimoura.news.articles.presentation

import androidx.lifecycle.LiveData
import com.github.pedrodimoura.news.articles.domain.usecase.ArticleTopHeadlines
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState

interface ArticleInteractor {

    interface View {
        fun fetch()
    }

    interface ViewModel {
        fun fetch(page: Int = 1)
        fun observeTopHeadlines(): LiveData<FlowState<ArticleTopHeadlines>>
    }

}