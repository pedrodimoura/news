package com.github.pedrodimoura.news.articles.presentation.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.github.pedrodimoura.news.R
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.presentation.adapter.ArticleItemDecoration
import com.github.pedrodimoura.news.articles.presentation.adapter.ArticleListAdapter
import com.github.pedrodimoura.news.articles.presentation.adapter.ArticleSpanSizeLookup
import com.github.pedrodimoura.news.articles.presentation.viewmodel.ArticleViewModel
import com.github.pedrodimoura.news.common.data.datasource.remote.NetworkCallState
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
    override val menuRes: Int = R.menu.menu_main

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

    private var forceReload = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        setupSwipeRefreshLayout()
        setupRecyclerView()
        observeTopHeadlines()
        fetchTopHeadlines()
    }

    private fun setupSwipeRefreshLayout() {
        articleSwipeRefreshLayout.apply {
            setOnRefreshListener {
                fetchTopHeadlines(true)
                changeEmptyViewVisibility(false)
            }
            setColorSchemeColors(ContextCompat.getColor(context, R.color.colorPrimary))
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

    private fun observeTopHeadlines() {
        observe(articleViewModel.observeTopHeadlines()) { flowState ->
            when (flowState) {
                is FlowState.Success -> {
                    flowState.data?.let { topHeadlinesResult ->
                        observe(topHeadlinesResult.pagedListArticles) {
                            articleListAdapter.submitList(it)
                        }

                        observe(topHeadlinesResult.networkCallState) { networkCallState ->
                            when (networkCallState) {
                                is NetworkCallState.Requesting -> {
                                    changeEmptyViewVisibility(false)
                                    showSwipeRefresh(true)
                                }
                                is NetworkCallState.Done -> {
                                    showSwipeRefresh(false)
                                }
                                is NetworkCallState.Failed -> changeEmptyViewVisibility(true)
                            }
                        }
                    } ?: handleSuccessWithNoData()
                }
            }
        }
    }

    private fun showSwipeRefresh(show: Boolean) {
        forceReload = show
        articleSwipeRefreshLayout.isRefreshing = show
    }

    private fun fetchTopHeadlines(invalidatingDataSource: Boolean = false) {
        if (invalidatingDataSource)
            invalidateDataSource()

        articleViewModel.fetch(DEFAULT_COUNTRY, DEFAULT_PAGE_SIZE, invalidatingDataSource)
    }

    private fun showEmptyView(pagedListAdapter: PagedList<Article>) =
        changeEmptyViewVisibility(pagedListAdapter.isEmpty())

    private fun changeEmptyViewVisibility(visible: Boolean) {
        articleRecyclerView.visibility = if (visible) View.GONE else View.VISIBLE
        emptyView.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun invalidateDataSource() = articleViewModel.clearArticles()

    private fun handleSuccessWithNoData() {
        Timber.d("Success without data")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_countries -> {
                val c = CountriesBottomSheetDialogFragment()
                c.showNow(supportFragmentManager, CountriesBottomSheetDialogFragment.TAG)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val DEFAULT_COUNTRY = "de"
        private const val DEFAULT_PAGE_SIZE = 21
    }

}
