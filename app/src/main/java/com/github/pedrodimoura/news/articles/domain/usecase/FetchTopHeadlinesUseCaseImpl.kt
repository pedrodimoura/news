package com.github.pedrodimoura.news.articles.domain.usecase

import androidx.lifecycle.LiveData
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams
import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository
import com.github.pedrodimoura.news.articles.exception.RemoteErrorTransformer
import com.github.pedrodimoura.news.common.domain.usecase.DomainInteractor
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState
import org.koin.core.error.NoParameterFoundException
import retrofit2.HttpException
import timber.log.Timber

typealias ArticleTopHeadlines = LiveData<List<Article>>
typealias FetchTopHeadlinesUseCase = DomainInteractor<TopHeadlinesParams, ArticleTopHeadlines>

class FetchTopHeadlinesUseCaseImpl(
    private val articleRepository: ArticleRepository
) : FetchTopHeadlinesUseCase {

    override suspend fun execute(params: TopHeadlinesParams?): FlowState<ArticleTopHeadlines> {
        return params?.let { topHeadlinesParams ->
            try {
                val result = articleRepository.fetchTopHeadlines(topHeadlinesParams)
                FlowState.Success(result)
            } catch (e: Exception) {
                e as HttpException
                Timber.d("Error Code ${e.code()}")
                FlowState.Error(RemoteErrorTransformer.transform(e))
            }
        } ?: FlowState.Error(NoParameterFoundException("No params found on ${this::class.java.simpleName}"))
    }
}