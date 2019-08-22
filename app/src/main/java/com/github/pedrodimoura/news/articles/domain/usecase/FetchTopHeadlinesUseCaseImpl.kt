package com.github.pedrodimoura.news.articles.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository
import com.github.pedrodimoura.news.common.domain.usecase.DomainInteractor
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState

typealias ArticlePagedList = LiveData<PagedList<Article>>
typealias FetchTopHeadlinesUseCase = DomainInteractor<Nothing, ArticlePagedList>

class FetchTopHeadlinesUseCaseImpl(
    private val articleRepository: ArticleRepository
) : FetchTopHeadlinesUseCase {

    override suspend fun execute(params: Nothing?): FlowState<ArticlePagedList> {
        return try {
            FlowState.Success(articleRepository.fetchTopHeadlines())
        } catch (e: Exception) {
            FlowState.Error(e)
        }
    }
}