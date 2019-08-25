package com.github.pedrodimoura.news.articles.presentation.adapter

import androidx.recyclerview.widget.GridLayoutManager
import com.github.pedrodimoura.news.common.util.isMultiplyOf

class ArticleSpanSizeLookup(private val totalColumns: Int) : GridLayoutManager.SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {
        return if (position.isMultiplyOf(7)) { totalColumns } else { 1 }
    }
}