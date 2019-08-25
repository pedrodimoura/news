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
import com.github.pedrodimoura.news.common.presentation.ui.BaseActivity
import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope
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

    private val articleListAdapter: ArticleListAdapter by currentScope.inject()
    private val articleItemDecoration: ArticleItemDecoration by currentScope.inject {
        parametersOf(articleRecyclerViewColumnsCount, articleItemMargin)
    }
    private val articleSpanSizeLookup: ArticleSpanSizeLookup by currentScope.inject {
        parametersOf(articleRecyclerViewColumnsCount)
    }

    private val articleViewModel: ArticleViewModel by inject()

    private var currentArticles: List<Article> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        setupRecyclerView()
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

    private fun observeTopHeadlines() {
        articleViewModel.observeTopHeadlines().observe(this, Observer {
            when (it) {
                is FlowState.Loading -> Timber.d("Loading")
                is FlowState.Success -> {
                    Timber.d("Success")
                    it.data?.let { articleTopHeadlines ->
                        articleTopHeadlines.observe(this, Observer { articles ->
                            isContentAlreadyLoaded = true
                            currentArticles = articles
                            articleListAdapter.submitList(articles)
                        })
                    } ?: handleSuccessWithNoData()
                }
                is FlowState.Error -> Timber.d("Error ${it.throwable}")
                is FlowState.Done -> Timber.d("Done")
            }
        })
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
        saveContentLoaded(currentArticles as ArrayList<Article>, outState)
    }

    private fun handleSuccessWithNoData() {
        Timber.d("Success without data")
    }

}
