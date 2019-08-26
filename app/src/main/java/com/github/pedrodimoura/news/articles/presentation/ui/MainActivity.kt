package com.github.pedrodimoura.news.articles.presentation.ui

import android.os.Bundle
import android.widget.Toast
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

    private var isFetchingNextPage = false

    private var forceReload = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        setupSwipeRefreshLayout()
        setupRecyclerView()
        observePagingActionChanges()
        observeTopHeadlines()
        fetchTopHeadlines(savedInstanceState)
    }

    private fun setupSwipeRefreshLayout() {
        articleSwipeRefreshLayout.setOnRefreshListener {
            showSwipeRefresh(true)
            articleViewModel.fetch()
        }
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
                        articleViewModel.fetch(it.page)
                    }
                }
                is PagingAction.Idle -> isFetchingNextPage = false
            }
        }
    }

    private fun observeTopHeadlines() {
        observe(articleViewModel.observeTopHeadlines()) {
            when (it) {
                is FlowState.Loading -> {  }
                is FlowState.Success -> {
                    it.data?.let { articleTopHeadlines ->
                        articleTopHeadlines.observe(this, Observer { articles ->
                            isContentAlreadyLoaded = true
                            articleListAdapter.add(articles, forceReload)
                        })
                    } ?: handleSuccessWithNoData()
                }
                is FlowState.Error -> {
                    articleListAdapter.reached()

                    Toast.makeText(this@MainActivity, it.throwable.toString(), Toast.LENGTH_LONG).show()
                }
                is FlowState.Done -> showSwipeRefresh(false)
            }
        }
    }

    private fun showSwipeRefresh(show: Boolean) {
        forceReload = show
        articleSwipeRefreshLayout.isRefreshing = show
    }

    private fun fetchTopHeadlines(savedInstanceState: Bundle?) {
        if (!isContentAlreadyLoaded) {
            articleViewModel.fetch()
        } else {
            val savedArticles: List<Article> =
                savedInstanceState?.getParcelableArrayList(CONTENT_BUNDLE_KEY) ?: emptyList()
            articleListAdapter.submitList(savedArticles)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveContentLoaded(articleListAdapter.getArticles(), outState)
    }

    private fun handleSuccessWithNoData() {
        Timber.d("Success without data")
    }

}
