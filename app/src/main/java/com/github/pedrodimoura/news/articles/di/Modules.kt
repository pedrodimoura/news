package com.github.pedrodimoura.news.articles.di

import com.github.pedrodimoura.news.articles.data.datasource.remote.ArticleRemoteDataSource
import com.github.pedrodimoura.news.articles.data.datasource.remote.ArticleService
import com.github.pedrodimoura.news.articles.data.datasource.remote.impl.ArticlePageKeyDataSource
import com.github.pedrodimoura.news.articles.data.datasource.remote.impl.ArticlePagingDataSource
import com.github.pedrodimoura.news.articles.data.datasource.remote.impl.ArticleRemoteDataSourceImpl
import com.github.pedrodimoura.news.articles.data.repository.ArticleRepositoryImpl
import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository
import com.github.pedrodimoura.news.articles.domain.usecase.FetchTopHeadlinesUseCaseImpl
import com.github.pedrodimoura.news.common.presentation.viewmodel.ThreadContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val THREAD_CONTEXT_PROVIDER_NAME = "article_thread_context_provider"
const val JOB_NAME = "article_job"

val articleModule = module {
    factory(named(THREAD_CONTEXT_PROVIDER_NAME)) { ThreadContextProvider() }
    factory(named(JOB_NAME)) { Job() }
    single {
        ArticlePageKeyDataSource(
            articleService = get<Retrofit>().create(ArticleService::class.java),
            coroutineScope = CoroutineScope(
                get<ThreadContextProvider>(named(THREAD_CONTEXT_PROVIDER_NAME)).io + get<Job>(named(JOB_NAME))),
            threadContextProvider = get(named(THREAD_CONTEXT_PROVIDER_NAME)))
    }
    single { ArticlePagingDataSource(articlePageKeyDataSource = get()) }
    single<ArticleRemoteDataSource> { ArticleRemoteDataSourceImpl(articlePagingDataSource = get()) }
    single<ArticleRepository> { ArticleRepositoryImpl(articleRemoteDataSource = get()) }
    factory { FetchTopHeadlinesUseCaseImpl(articleRepository = get()) }
}