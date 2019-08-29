package com.github.pedrodimoura.news.articles.presentation

import androidx.lifecycle.LiveData
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesResult
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState

interface ArticleInteractor {

    interface View {
        fun fetch()
    }

    interface ViewModel {
        fun fetch(country: String, pageSize: Int = 21, invalidatingSource: Boolean = false)
        fun observeTopHeadlines(): LiveData<FlowState<TopHeadlinesResult>>
        fun clearArticles()
    }

}