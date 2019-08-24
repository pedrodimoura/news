package com.github.pedrodimoura.news.articles.data.datasource.remote.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.pedrodimoura.news.articles.data.datasource.ArticleDataSource
import com.github.pedrodimoura.news.articles.data.datasource.remote.ArticleService
import com.github.pedrodimoura.news.articles.data.datasource.remote.entity.ArticleRemote
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams

class ArticleRemoteDataSource(
    private val articleService: ArticleService
) : ArticleDataSource<ArticleRemote> {

    private val articleRemoteLiveData = MutableLiveData<List<ArticleRemote>>()

    override suspend fun fetchTopHeadlines(topHeadlinesParams: TopHeadlinesParams): LiveData<List<ArticleRemote>> {
        val result = articleService.fetchTopHeadlines(
            topHeadlinesParams.country,
            topHeadlinesParams.page,
            topHeadlinesParams.pageSize
        )
        articleRemoteLiveData.postValue(result.articles)
        return articleRemoteLiveData
    }

    override suspend fun getAvailableTopHeadlines(): LiveData<List<ArticleRemote>> =
        throw UnsupportedOperationException()

    override suspend fun save(article: ArticleRemote) = throw UnsupportedOperationException()
}