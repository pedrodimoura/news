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
import org.koin.core.error.NoParameterFoundException

typealias FetchTopHeadlinesUseCase = DomainInteractor<TopHeadlinesParams, LiveData<PagedList<Article>>>

class FetchTopHeadlinesUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val networkBoundaryCallback: RemoteBoundary<TopHeadlinesParams, Article>
) : FetchTopHeadlinesUseCase {

    override suspend fun execute(params: TopHeadlinesParams?): FlowState<LiveData<PagedList<Article>>> {
        return params?.let {
            try {
                val result = articleRepository.getAvailableTopHeadlines()
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(true)
                    .setPrefetchDistance(10)
                    .setInitialLoadSizeHint(21)
                    .setPageSize(21)
                    .build()

                networkBoundaryCallback.params = it

                val livePagedList = LivePagedListBuilder(result, config)
                    .setBoundaryCallback(networkBoundaryCallback)
                FlowState.Success(livePagedList.build())
            } catch (e: Exception) {
                FlowState.Error(RemoteErrorTransformer.transform(e))
            }
        } ?: FlowState.Error(NoParameterFoundException("No params found for ${this::class.simpleName}"))
    }

}