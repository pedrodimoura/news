package com.github.pedrodimoura.news.articles.domain.entity

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.github.pedrodimoura.news.common.data.datasource.remote.NetworkCallState

data class TopHeadlinesResult(
    val pagedListArticles: LiveData<PagedList<Article>>,
    val networkCallState: LiveData<NetworkCallState>
)