package com.github.pedrodimoura.news.articles.domain.usecase

import androidx.lifecycle.LiveData
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams
import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository
import com.github.pedrodimoura.news.common.domain.usecase.DomainInteractor
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState
import org.koin.core.error.NoParameterFoundException

typealias ArticleTopHeadlines = LiveData<List<Article>>
typealias FetchTopHeadlinesUseCase = DomainInteractor<TopHeadlinesParams, ArticleTopHeadlines>

class FetchTopHeadlinesUseCaseImpl(
    private val articleRepository: ArticleRepository
) : FetchTopHeadlinesUseCase {

    override suspend fun execute(params: TopHeadlinesParams?): FlowState<ArticleTopHeadlines> {
        return params?.let { topHeadlinesParams ->
            try {
                FlowState.Success(articleRepository.fetchTopHeadlines(topHeadlinesParams))
            } catch (e: Exception) {
                FlowState.Error(e)
            }
        } ?: FlowState.Error(NoParameterFoundException("No params found on ${this::class.java.simpleName}"))
    }
}