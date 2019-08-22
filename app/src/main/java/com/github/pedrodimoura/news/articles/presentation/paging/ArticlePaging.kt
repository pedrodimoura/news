package com.github.pedrodimoura.news.articles.presentation.paging

import androidx.paging.PageKeyedDataSource
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlines
import com.github.pedrodimoura.news.common.presentation.viewmodel.ThreadContextProvider

class ArticlePaging(private val threadContextProvider: ThreadContextProvider) : PageKeyedDataSource<Int, TopHeadlines>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, TopHeadlines>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TopHeadlines>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TopHeadlines>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}