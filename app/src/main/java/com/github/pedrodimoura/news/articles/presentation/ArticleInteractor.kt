package com.github.pedrodimoura.news.articles.presentation

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState

interface ArticleInteractor {

    interface View {
        fun fetch()
    }

    interface ViewModel {
        fun fetch(country: String, pageSize: Int = 21)
        fun observeTopHeadlines(): LiveData<FlowState<LiveData<PagedList<Article>>>>
        fun clearArticles()
    }

}