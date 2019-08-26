package com.github.pedrodimoura.news.articles.presentation.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.github.pedrodimoura.news.R
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.presentation.adapter.ArticleItemDecoration
import com.github.pedrodimoura.news.articles.presentation.adapter.ArticleListAdapter
import com.github.pedrodimoura.news.articles.presentation.adapter.ArticleSpanSizeLookup
import com.github.pedrodimoura.news.articles.presentation.viewmodel.ArticleViewModel
import com.github.pedrodimoura.news.common.presentation.adapter.PagingAction
import com.github.pedrodimoura.news.common.presentation.ui.BaseActivity
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState
import com.github.pedrodimoura.news.common.util.observe
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class MainActivity : BaseActivity() {

    override val layoutResId: Int = R.layout.activity_main
    private val articleRecyclerViewColumnsCount: Int by lazy {
        resources.getInteger(R.integer.article_recycler_view_columns_count)
    }
    private val articleItemMargin: Float by lazy {
        resources.getDimension(R.dimen.article_item_margin)
    }

    private val articleListAdapter: ArticleListAdapter by inject()
    private val articleItemDecoration: ArticleItemDecoration by currentScope.inject {
        parametersOf(articleRecyclerViewColumnsCount, articleItemMargin)
    }
    private val articleSpanSizeLookup: ArticleSpanSizeLookup by currentScope.inject {
        parametersOf(articleRecyclerViewColumnsCount)
    }

    private val articleViewModel: ArticleViewModel by viewModel()

    private var currentArticles = ArrayList<Article>()

    private var isFetchingNextPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        setupRecyclerView()
        observePagingActionChanges()
        observeTopHeadlines()
        fetchTopHeadlines(savedInstanceState)
    }

    private fun setupRecyclerView() {
        articleRecyclerView.apply {
            val gridLayoutManager = GridLayoutManager(context, articleRecyclerViewColumnsCount)
            gridLayoutManager.isSmoothScrollbarEnabled = true
            gridLayoutManager.spanSizeLookup = articleSpanSizeLookup
            adapter = articleListAdapter
            layoutManager = gridLayoutManager
            addItemDecoration(articleItemDecoration)
        }
    }

    private fun observePagingActionChanges() {
        observe(articleListAdapter.observePagingAction()) {
            when (it) {
                is PagingAction.FetchNext -> {
                    if (!isFetchingNextPage) {
                        isFetchingNextPage = true
                        Timber.d("Fetch Next Page")
                        articleViewModel.fetch(it.page)
                    }
                }
                is PagingAction.Idle -> {
                    isFetchingNextPage = false
                    Timber.d("Idle")
                }
            }
        }
    }

    private fun observeTopHeadlines() {
        observe(articleViewModel.observeTopHeadlines()) {
            when (it) {
                is FlowState.Loading -> Timber.d("Loading")
                is FlowState.Success -> {
                    Timber.d("Success")
                    it.data?.let { articleTopHeadlines ->
                        articleTopHeadlines.observe(this, Observer { articles ->
                            isContentAlreadyLoaded = true
                            currentArticles.addAll(articles)
                            articleListAdapter.add(currentArticles)
                        })
                    } ?: handleSuccessWithNoData()
                }
                is FlowState.Error -> Timber.d("Error ${it.throwable}")
                is FlowState.Done -> Timber.d("Done")
            }
        }
    }

    private fun fetchTopHeadlines(savedInstanceState: Bundle?) {
        if (!isContentAlreadyLoaded) {
            articleViewModel.fetch()
        } else {
            currentArticles =
                savedInstanceState?.getParcelableArrayList(CONTENT_BUNDLE_KEY) ?: currentArticles
            articleListAdapter.submitList(currentArticles)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveContentLoaded(currentArticles, outState)
    }

    private fun handleSuccessWithNoData() {
        Timber.d("Success without data")
    }

}
