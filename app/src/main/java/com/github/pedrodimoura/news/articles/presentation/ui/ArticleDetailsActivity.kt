package com.github.pedrodimoura.news.articles.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.pedrodimoura.news.R
import com.github.pedrodimoura.news.articles.presentation.entity.ArticleView
import com.github.pedrodimoura.news.common.presentation.ui.BaseActivity
import kotlinx.android.synthetic.main.article_details.*
import kotlinx.android.synthetic.main.content_article_details.*

class ArticleDetailsActivity : BaseActivity() {

    override val layoutResId: Int = R.layout.article_details

    override val menuRes: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
        showArticleDetailsOnUI()
    }

    private fun setupToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun showArticleDetailsOnUI() {
        val articleView = intent.getParcelableExtra<ArticleView>(ArticleView.EXTRA_KEY)

        articleView?.apply {
            articleTitle.text = title

            Glide.with(this@ArticleDetailsActivity)
                .load(urlToImage)
                .placeholder(R.drawable.ic_newspaper_24dp)
                .error(R.drawable.ic_newspaper_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .into(articleImage)

            articleDescription.text = description

            articleGoToWebSite.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }

        }
    }

    override fun isDeviceConnected() {}

    override fun isDeviceDisconnected() {}

}