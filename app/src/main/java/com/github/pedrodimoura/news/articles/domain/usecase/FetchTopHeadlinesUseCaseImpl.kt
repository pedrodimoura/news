package com.github.pedrodimoura.news.articles.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams
import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository
import com.github.pedrodimoura.news.articles.exception.RemoteErrorTransformer
import com.github.pedrodimoura.news.common.data.datasource.remote.RemoteBoundary
import com.github.pedrodimoura.news.common.domain.usecase.DomainInteractor
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState

typealias FetchTopHeadlinesUseCase = DomainInteractor<Nothing, LiveData<PagedList<Article>>>

class FetchTopHeadlinesUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val networkBoundaryCallback: RemoteBoundary<TopHeadlinesParams, Article>
) : FetchTopHeadlinesUseCase {

    override suspend fun execute(params: Nothing?): FlowState<LiveData<PagedList<Article>>> {
        return try {
            val result = articleRepository.getAvailableTopHeadlines()
            val config = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPrefetchDistance(10)
                .setInitialLoadSizeHint(21)
                .setPageSize(21)
                .build()
            networkBoundaryCallback.params
            val livePagedList = LivePagedListBuilder(result, config)
                .setBoundaryCallback(networkBoundaryCallback)
            FlowState.Success(livePagedList.build())
        } catch (e: Exception) {
            FlowState.Error(RemoteErrorTransformer.transform(e))
        }
    }

}