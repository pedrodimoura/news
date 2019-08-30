package com.github.pedrodimoura.news.articles.presentation.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.pedrodimoura.news.common.util.isMultiplyOf

class ArticleItemDecoration(
    private val totalColumns: Int,
    private val margin: Int
) : RecyclerView.ItemDecoration() {

    private var isOddExpected = false
    private var lastPosition = -1

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val currentPosition = parent.getChildAdapterPosition(view)

        val halfMargin = margin.div(2)

        with(outRect) {
            set(halfMargin, halfMargin, halfMargin, halfMargin)

            if (currentPosition.isMultiplyOf(7)) {
                if (currentPosition == 0)
                    top = margin

                if (currentPosition == (state.itemCount - 1))
                    bottom = margin

                left = margin
                right = margin
            }

            if (isOnTheRight(currentPosition))
                right = margin

            if (isOnTheLeft(currentPosition))
                left = margin
        }
    }

    private fun isOnTheRight(currentPosition: Int): Boolean {
        return when (totalColumns) {
            PORTRAIT -> {
                val result = (currentPosition + 1).rem(7)
                PORTRAIT_RIGHT.contains(result)
            }
            LANDSCAPE -> {
                val result = currentPosition.rem(7)
                LANDSCAPE_RIGHT.contains(result)
            }
            else -> false
        }
    }

    private fun isOnTheLeft(currentPosition: Int): Boolean {
        return when (totalColumns) {
            PORTRAIT -> {
                val result = (currentPosition + 1).rem(7)
                PORTRAIT_LEFT.contains(result)
            }
            LANDSCAPE -> {
                val result = currentPosition.rem(7)
                LANDSCAPE_LEFT.contains(result)
            }
            else -> false
        }
    }

    companion object {
        @JvmStatic
        val LANDSCAPE_LEFT = arrayOf(1, 4)
        @JvmStatic
        val LANDSCAPE_RIGHT = arrayOf(3, 6)

        @JvmStatic
        val PORTRAIT_LEFT = arrayOf(2, 4, 6)
        @JvmStatic
        val PORTRAIT_RIGHT = arrayOf(3, 5, 0)

        const val PORTRAIT = 2
        const val LANDSCAPE = 3
    }

}