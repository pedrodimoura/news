package com.github.pedrodimoura.news.articles.presentation.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.github.pedrodimoura.news.R
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

    private val articleListAdapter: ArticleListAdapter by currentScope.inject()
    private val articleSpanSizeLookup: ArticleSpanSizeLookup by currentScope.inject {
        parametersOf(articleRecyclerViewColumnsCount)
    }

    private val articleViewModel: ArticleViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        articleRecyclerView.apply {
            val gridLayoutManager = GridLayoutManager(context, articleRecyclerViewColumnsCount)
            gridLayoutManager.spanSizeLookup = articleSpanSizeLookup
            adapter = articleListAdapter
            layoutManager = gridLayoutManager
        }

        articleViewModel.observeTopHeadlines().observe(this, Observer {
            when (it) {
                is FlowState.Loading -> Timber.d("Loading")
                is FlowState.Success -> {
                    Timber.d("Success")
                    it.data?.let { articleTopHeadlines ->
                        articleTopHeadlines.observe(this, Observer { articles ->
                            articleListAdapter.submitList(articles)
                        })
                    } ?: handleSuccessWithNoData()
                }
                is FlowState.Error -> Timber.d("Error ${it.throwable}")
                is FlowState.Done -> Timber.d("Done")
            }
        })

        articleViewModel.fetch()
    }

    private fun handleSuccessWithNoData() {
        Timber.d("Success without data")
    }

}
