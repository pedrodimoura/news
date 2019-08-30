package com.github.pedrodimoura.news.articles.domain

import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import com.github.pedrodimoura.news.LocalTest
import com.github.pedrodimoura.news.articles.di.KOIN_FETCH_ARTICLES_NAME
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesResult
import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository
import com.github.pedrodimoura.news.articles.domain.usecase.FetchTopHeadlinesUseCase
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import org.koin.core.error.NoParameterFoundException
import org.koin.core.qualifier.named
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito

class FetchTopHeadlinesUseCaseImplTest : LocalTest() {

    private val fetchTopHeadlinesUseCase: FetchTopHeadlinesUseCase by inject(
        named(KOIN_FETCH_ARTICLES_NAME)
    )

    private val articleRepository: ArticleRepository by inject()

    override fun setup() {
        super.setup()
        declareMock<ArticleRepository>()
    }

    @Test
    fun `Should execute WITH params from FetchTopHeadlinesUseCase return a Success FlowState`() {
        runBlocking {
            BDDMockito
                .`when`(articleRepository.getAvailableTopHeadlines())
                .thenReturn(dataSourceFactory)
            val result = fetchTopHeadlinesUseCase.execute(topHeadlinesParams)

            assertTrue(result is FlowState.Success)
            result as FlowState.Success
            assertTrue(result.data is TopHeadlinesResult)
        }
    }

    @Test
    fun `Should execute WITHOUT params from FetchTopHeadlinesUseCase throws a NoParamsFoundException`() {
        runBlocking {
            val result = fetchTopHeadlinesUseCase.execute()
            assertTrue(result is FlowState.Error)
            result as FlowState.Error
            assertTrue(result.throwable is NoParameterFoundException)
        }
    }

    companion object {

        @JvmStatic
        val topHeadlinesParams = TopHeadlinesParams("de", 1, 21)
        @JvmStatic
        val dataSourceFactory = object : DataSource.Factory<Int, Article>() {
            override fun create(): DataSource<Int, Article> {
                return object : PageKeyedDataSource<Int, Article>() {
                    override fun loadInitial(
                        params: LoadInitialParams<Int>,
                        callback: LoadInitialCallback<Int, Article>
                    ) {
                    }

                    override fun loadAfter(
                        params: LoadParams<Int>,
                        callback: LoadCallback<Int, Article>
                    ) {
                    }

                    override fun loadBefore(
                        params: LoadParams<Int>,
                        callback: LoadCallback<Int, Article>
                    ) {
                    }
                }
            }
        }
        @JvmStatic
        val livePagedList = LivePagedListBuilder(dataSourceFactory, 21).build()

    }

}