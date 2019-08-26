package com.github.pedrodimoura.news.articles.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.pedrodimoura.news.R
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.common.presentation.adapter.PagingAction
import com.github.pedrodimoura.news.common.presentation.adapter.PagingActionCallback
import kotlinx.android.synthetic.main.item_article.view.*
import timber.log.Timber

class ArticleListAdapter : ListAdapter<Article, ArticleListAdapter.ArticleViewHolder>(DIFF_UTIL),
    PagingActionCallback {

    private var currentPage: Int = 1
    private val pagingActionLiveData = MutableLiveData<PagingAction>()
    private var articles = ArrayList<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        article?.let { holder.title.text = it.title }
        holder.itemView.setOnClickListener { Timber.d("OnClick: ${article.title}") }
        sendPagingActionOn(position)
    }

    private fun sendPagingActionOn(position: Int) {
        val percent = ((position.toFloat() / itemCount) * 100).toInt()
        val pagingAction =
            if (percent >= 80) {
                currentPage++
                PagingAction.FetchNext(currentPage)
            } else { PagingAction.Idle }
        notifyPagingAction(pagingAction)
    }

    override fun notifyPagingAction(pagingAction: PagingAction) {
        pagingActionLiveData.value = pagingAction
    }

    fun observePagingAction(): LiveData<PagingAction> = pagingActionLiveData

    fun add(articles: ArrayList<Article>) {
        this.articles.addAll(articles)
        this.submitList(this.articles)
        this.notifyDataSetChanged()
    }

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.title
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