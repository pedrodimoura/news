package com.github.pedrodimoura.news.articles.domain.usecase

import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository
import com.github.pedrodimoura.news.common.domain.usecase.DomainInteractor
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState

typealias ClearArticlesUseCase = DomainInteractor<Nothing, Nothing>

class ClearArticlesUseCaseImpl(
    private val articleRepository: ArticleRepository
) : ClearArticlesUseCase {

    override suspend fun execute(params: Nothing?): FlowState<Nothing> {
        return try {
            articleRepository.clearArticles()
            FlowState.Success()
        } catch (e: Exception) {
            FlowState.Error(e)
        }
    }
}