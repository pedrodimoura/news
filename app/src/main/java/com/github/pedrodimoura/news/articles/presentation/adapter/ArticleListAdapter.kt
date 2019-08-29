package com.github.pedrodimoura.news.articles.presentation.adapter

import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.pedrodimoura.news.R
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.common.util.inflate
import com.github.pedrodimoura.news.common.util.isMultiplyOf
import kotlinx.android.synthetic.main.item_article.view.*

class ArticleListAdapter :
    PagedListAdapter<Article, ArticleListAdapter.ArticleViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(parent.inflate(R.layout.item_article, false))

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)

        article?.let {
            holder.title.text = it.title

            if (position.isMultiplyOf(7)) {
                holder.description.isSingleLine = false
                holder.description.maxLines = 3
                holder.description.text = it.content
            } else {
                holder.description.isSingleLine = true
                holder.description.text = it.description
            }

//            holder.description.text =
//                if (position.isMultiplyOf(7)) it.content else it.description

            holder.source.text =
                holder.itemView.context.getString(R.string.from_source, it.source.name)

            Glide.with(holder.itemView)
                .load(it.urlToImage)
                .placeholder(R.drawable.ic_newspaper_24dp)
                .error(R.drawable.ic_newspaper_24dp)
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .into(holder.image)

            val hoursAgo = DateUtils.getRelativeTimeSpanString(
                it.publishedAt.time,
                System.currentTimeMillis(),
                DateUtils.HOUR_IN_MILLIS
            )

            holder.publishedAt.text = hoursAgo
        }

    }

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.title
        val image: AppCompatImageView = view.articleImageView
        val description: TextView = view.description
        val source: TextView = view.source
        val publishedAt: TextView = view.publishedAt
    }

    companion object {
        @JvmStatic
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }

}