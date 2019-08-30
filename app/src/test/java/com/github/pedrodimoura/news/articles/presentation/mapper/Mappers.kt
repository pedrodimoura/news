package com.github.pedrodimoura.news.articles.presentation.mapper

import com.github.pedrodimoura.news.BaseTest
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.entity.Source
import com.github.pedrodimoura.news.articles.presentation.entity.ArticleView
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class Mappers : BaseTest() {

    override fun setup() {}

    override fun tearDown() {}

    @Test
    fun `Should asArticleView from Article return a valid ArticleView`() {
        val article = articleToParse.asArticleView()
        assertEquals(articleViewExpected, article)
    }

    companion object {

        @JvmStatic
        val date = Date()

        @JvmStatic
        val source = Source("name", "name")

        @JvmStatic
        val articleToParse = Article(
            0,
            source,
            "author",
            "title",
            "description",
            "url",
            "urlToImage",
            date,
            "content"
        )

        @JvmStatic
        val articleViewExpected = ArticleView(
            "title",
            "description",
            "content",
            date,
            "urlToImage",
            "url",
            "name"
        )
    }

}