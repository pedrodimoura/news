package com.github.pedrodimoura.news.articles.di

import com.github.pedrodimoura.news.articles.data.datasource.local.impl.ArticleLocalDataSource
import com.github.pedrodimoura.news.articles.data.datasource.remote.ArticleService
import com.github.pedrodimoura.news.articles.data.datasource.remote.impl.ArticleRemoteDataSource
import com.github.pedrodimoura.news.articles.data.repository.ArticleRepositoryImpl
import com.github.pedrodimoura.news.articles.domain.usecase.FetchTopHeadlinesUseCase
import com.github.pedrodimoura.news.articles.domain.usecase.FetchTopHeadlinesUseCaseImpl
import com.github.pedrodimoura.news.articles.presentation.adapter.ArticleListAdapter
import com.github.pedrodimoura.news.articles.presentation.adapter.ArticleSpanSize
import com.github.pedrodimoura.news.articles.presentation.ui.MainActivity
import com.github.pedrodimoura.news.articles.presentation.viewmodel.ArticleViewModel
import com.github.pedrodimoura.news.common.data.datasource.local.NewsRoomDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val articleModule = module {
    single { get<Retrofit>().create(ArticleService::class.java) }
    single { get<NewsRoomDatabase>().articleDAO() }
    single { ArticleLocalDataSource(articleDAO = get()) }
    single { ArticleRemoteDataSource(articleService = get()) }
    single {
        ArticleRepositoryImpl(articleLocalDataSource = get(), articleRemoteDataSource = get())
    }

    factory<FetchTopHeadlinesUseCase> { FetchTopHeadlinesUseCaseImpl(articleRepository = get()) }
    viewModel {
        ArticleViewModel(
            fetchTopHeadlinesUseCase = get(),
            threadContextProvider = get()
        )
    }

    scope(named<MainActivity>()) {
        scoped { ArticleListAdapter() }
        scoped { (articleRecyclerViewColumnsCount: Int) ->
            ArticleSpanSize(articleRecyclerViewColumnsCount)
        }
    }

}