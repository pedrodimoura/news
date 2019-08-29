package com.github.pedrodimoura.news.articles.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.github.pedrodimoura.news.articles.data.datasource.local.impl.ArticleLocalDataSource
import com.github.pedrodimoura.news.articles.data.datasource.remote.ArticleService
import com.github.pedrodimoura.news.articles.data.datasource.remote.impl.ArticleRemoteBoundaryCallback
import com.github.pedrodimoura.news.articles.data.datasource.remote.impl.ArticleRemoteDataSource
import com.github.pedrodimoura.news.articles.data.repository.ArticleRepositoryImpl
import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository
import com.github.pedrodimoura.news.articles.domain.usecase.AddNewArticleImpl
import com.github.pedrodimoura.news.articles.domain.usecase.ClearArticlesUseCaseImpl
import com.github.pedrodimoura.news.articles.domain.usecase.FetchTopHeadlinesUseCaseImpl
import com.github.pedrodimoura.news.articles.presentation.adapter.ArticleItemDecoration
import com.github.pedrodimoura.news.articles.presentation.adapter.ArticleListAdapter
import com.github.pedrodimoura.news.articles.presentation.adapter.ArticleSpanSizeLookup
import com.github.pedrodimoura.news.articles.presentation.ui.MainActivity
import com.github.pedrodimoura.news.articles.presentation.viewmodel.ArticleViewModel
import com.github.pedrodimoura.news.common.data.datasource.local.NewsRoomDatabase
import com.github.pedrodimoura.news.common.presentation.lifecycle.NetworkCallback
import com.github.pedrodimoura.news.common.presentation.lifecycle.NetworkLifecycleObserver
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val KOIN_ARTICLE_LOCAL_DATA_SOURCE_NAME = "articleLocalDataSource"
const val KOIN_ARTICLE_REMOTE_DATA_SOURCE_NAME = "articleRemoteDataSource"
const val KOIN_FETCH_ARTICLES_NAME = "fetchArticles"
const val KOIN_CLEAR_ARTICLES_NAME = "fetchClear"
const val KOIN_ADD_NEW_ARTICLE_NAME = "addNewArticle"
const val KOIN_ARTICLE_REMOTE_BOUNDARY = "articleRemoteBoundary"

val articleModule = module {
    single { get<Retrofit>().create(ArticleService::class.java) }
    single { get<NewsRoomDatabase>().articleDAO() }
    single(named(KOIN_ARTICLE_LOCAL_DATA_SOURCE_NAME)) { ArticleLocalDataSource(articleDAO = get()) }
    single(named(KOIN_ARTICLE_REMOTE_DATA_SOURCE_NAME)) { ArticleRemoteDataSource(articleService = get()) }
    single<ArticleRepository> {
        ArticleRepositoryImpl(
            articleLocalDataSource = get(named(KOIN_ARTICLE_LOCAL_DATA_SOURCE_NAME)),
            articleRemoteDataSource = get(named(KOIN_ARTICLE_REMOTE_DATA_SOURCE_NAME))
        )
    }

    single(named(KOIN_ARTICLE_REMOTE_BOUNDARY)) {
        ArticleRemoteBoundaryCallback(
            articleRepository = get(),
            threadContextProvider = get()
        )
    }

    factory(named(KOIN_FETCH_ARTICLES_NAME)) {
        FetchTopHeadlinesUseCaseImpl(
            articleRepository = get(),
            networkBoundaryCallback = get(named(KOIN_ARTICLE_REMOTE_BOUNDARY))
        )
    }
    factory(named(KOIN_ADD_NEW_ARTICLE_NAME)) { AddNewArticleImpl(articleRepository = get()) }
    factory(named(KOIN_CLEAR_ARTICLES_NAME)) { ClearArticlesUseCaseImpl(articleRepository = get()) }
    viewModel {
        ArticleViewModel(
            fetchTopHeadlinesUseCase = get(named(KOIN_FETCH_ARTICLES_NAME)),
            clearArticlesUseCase = get(named(KOIN_CLEAR_ARTICLES_NAME)),
            threadContextProvider = get()
        )
    }

    single { ArticleListAdapter() }

    single {
        androidApplication().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    single { NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build() }

    scope(named<MainActivity>()) {
        scoped { (totalColumns: Int, margin: Float) ->
            ArticleItemDecoration(totalColumns, margin.toInt())
        }
        scoped { (articleRecyclerViewColumnsCount: Int) ->
            ArticleSpanSizeLookup(articleRecyclerViewColumnsCount)
        }

        scoped { NetworkCallback() }
        scoped {
            NetworkLifecycleObserver(
                connectivityManager = get(),
                networkRequest = get(),
                networkCallback = get()
            )
        }
    }

}