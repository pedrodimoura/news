package com.github.pedrodimoura.news.articles.domain.uc

import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlines
import com.github.pedrodimoura.news.articles.domain.repository.TopHeadlinesRepository
import com.github.pedrodimoura.news.common.domain.DomainInteractor
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState

typealias FetchTopHeadlinesUseCase = DomainInteractor<Nothing, TopHeadlines>

class FetchTopHeadlinesUseCaseImpl(
    private val topHeadlinesRepository: TopHeadlinesRepository
) : FetchTopHeadlinesUseCase {

    override suspend fun execute(params: Nothing?): FlowState<TopHeadlines> {
        return try {
            FlowState.Success(topHeadlinesRepository.fetch())
        } catch (e: Exception) {
            FlowState.Error(e)
        }
    }

}