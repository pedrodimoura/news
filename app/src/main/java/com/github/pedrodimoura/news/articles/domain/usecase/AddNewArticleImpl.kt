package com.github.pedrodimoura.news.articles.domain.usecase

import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository
import com.github.pedrodimoura.news.common.domain.usecase.DomainInteractor
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState

typealias AddNewArticle = DomainInteractor<Article, Int>

class AddNewArticleImpl(private val articleRepository: ArticleRepository) : AddNewArticle {

    override suspend fun execute(params: Article?): FlowState<Int> {
        return try {
            articleRepository.save(arrayListOf(params!!))
            FlowState.Success(1)
        } catch (e: Exception) {
            FlowState.Error(e)
        }
    }

}