package com.github.pedrodimoura.news.articles.presentation.adapter

import androidx.recyclerview.widget.GridLayoutManager

class ArticleSpanSize(private val totalColumns: Int) : GridLayoutManager.SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int = if (position == 0) totalColumns else 1
}