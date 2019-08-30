package com.github.pedrodimoura.news.articles.presentation.viewmodel

import android.database.SQLException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.github.pedrodimoura.news.LocalTest
import com.github.pedrodimoura.news.NetworkTest
import com.github.pedrodimoura.news.articles.domain.FetchTopHeadlinesUseCaseImplTest
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesParams
import com.github.pedrodimoura.news.articles.domain.entity.TopHeadlinesResult
import com.github.pedrodimoura.news.articles.domain.repository.ArticleRepository
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*

class ArticleViewModelTest : LocalTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val articleViewModel: ArticleViewModel by inject()

    private val articleRepository: ArticleRepository by inject()

    @Mock
    private lateinit var observer: Observer<FlowState<*>>

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<FlowState<TopHeadlinesResult>>

    override fun setup() {
        super.setup()
        getKoin().declareMock<ArticleRepository>()
    }

    override fun tearDown() {
        super.tearDown()
        articleViewModel.observeTopHeadlines().removeObserver(observer)
    }

    @Test
    fun `Should fetch from ArticleViewModel notify a FlowState Success on LiveData`() =
        runBlocking {

            val t = TopHeadlinesParams("de", 1, 21, false)

            `when`(articleRepository.getAvailableTopHeadlines()).thenReturn(
                FetchTopHeadlinesUseCaseImplTest.dataSourceFactory
            )

            articleViewModel.observeTopHeadlines().observeForever(observer)

            articleViewModel.fetch(t.country, t.pageSize, t.invalidatingSource)

            verify(observer, times(3)).onChanged(argumentCaptor.capture())

            assertTrue(argumentCaptor.allValues[0] is FlowState.Loading)
            assertTrue(argumentCaptor.allValues[1] is FlowState.Success)
            assertTrue(argumentCaptor.allValues[2] is FlowState.Done)

        }

    @Test
    fun `Should fetch from ArticleViewModel notify with a FlowState Error on LiveData when failed`() =
        runBlocking {
            val t = TopHeadlinesParams("de", 1, 21, false)

            `when`(articleRepository.getAvailableTopHeadlines()).thenThrow(SQLException())

            articleViewModel.observeTopHeadlines().observeForever(observer)

            articleViewModel.fetch(t.country, t.pageSize, t.invalidatingSource)

            verify(observer, times(3)).onChanged(argumentCaptor.capture())

            assertTrue(argumentCaptor.allValues[0] is FlowState.Loading)
            assertTrue(argumentCaptor.allValues[1] is FlowState.Error)
            assertTrue(argumentCaptor.allValues[2] is FlowState.Done)
        }


}