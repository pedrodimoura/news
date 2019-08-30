package com.github.pedrodimoura.news.articles.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.github.pedrodimoura.news.R
import com.github.pedrodimoura.news.articles.presentation.ArticleInteractor
import com.github.pedrodimoura.news.articles.presentation.adapter.ArticleItemDecoration
import com.github.pedrodimoura.news.articles.presentation.adapter.ArticleListAdapter
import com.github.pedrodimoura.news.articles.presentation.adapter.ArticleSpanSizeLookup
import com.github.pedrodimoura.news.articles.presentation.entity.ArticleView
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

class MainActivity : BaseActivity(), ArticleInteractor.View,
    ArticleInteractor.View.AdapterCallback<ArticleView> {

    override val layoutResId: Int = R.layout.activity_main
    override val menuRes: Int = R.menu.menu_main

    private val articleRecyclerViewColumnsCount: Int by lazy {
        resources.getInteger(R.integer.article_recycler_view_columns_count)
    }
    private val articleItemMargin: Float by lazy {
        resources.getDimension(R.dimen.article_item_margin)
    }

    private val articleListAdapter: ArticleListAdapter by inject { parametersOf(this) }
    private val articleItemDecoration: ArticleItemDecoration by currentScope.inject {
        parametersOf(articleRecyclerViewColumnsCount, articleItemMargin)
    }
    private val articleSpanSizeLookup: ArticleSpanSizeLookup by currentScope.inject {
        parametersOf(articleRecyclerViewColumnsCount)
    }

    private val articleViewModel: ArticleViewModel by viewModel()

    private var forceReload = false

    private var clearDatabase = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        observeTopHeadlines()
        fetchTopHeadlines()
    }

    override fun setupView() {
        setSupportActionBar(toolbar)
        setupSwipeRefreshLayout()
        setupRecyclerView()
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
                                    showSwipeRefresh(true)
                                    changeEmptyViewVisibility(false)
                                }
                                is NetworkCallState.Done -> showSwipeRefresh(false)
                                is NetworkCallState.Failed -> showMessageNetworkCallFailed()
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

    override fun fetchTopHeadlines(invalidatingDataSource: Boolean) {
        if (invalidatingDataSource)
            invalidateDataSource()

        articleViewModel.fetch(DEFAULT_COUNTRY, DEFAULT_PAGE_SIZE, invalidatingDataSource)
    }

    private fun changeEmptyViewVisibility(visible: Boolean) {
        articleRecyclerView.visibility = if (visible) View.GONE else View.VISIBLE
        emptyView.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun invalidateDataSource() {
        if (clearDatabase)
            articleViewModel.clearArticles()
    }

    private fun handleSuccessWithNoData() = changeEmptyViewVisibility(true)

    private fun showMessageNetworkCallFailed() {
        Toast
            .makeText(this@MainActivity, R.string.default_error_message, Toast.LENGTH_SHORT)
            .show()
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

    override fun onItemAdapterClick(t: ArticleView) {
        val intent = Intent(this, ArticleDetailsActivity::class.java)
        intent.putExtra(ArticleView.EXTRA_KEY, t)
        startActivity(intent)
    }

    override fun isDeviceConnected() {
        clearDatabase = true
    }

    override fun isDeviceDisconnected() {
        clearDatabase = false
        Toast.makeText(this, R.string.device_network_disconnected, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val DEFAULT_COUNTRY = "de"
        private const val DEFAULT_PAGE_SIZE = 21
    }

}
