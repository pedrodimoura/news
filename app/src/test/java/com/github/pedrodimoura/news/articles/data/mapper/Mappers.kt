package com.github.pedrodimoura.news.articles.data.mapper

import com.github.pedrodimoura.news.BaseTest
import com.github.pedrodimoura.news.LocalTest
import com.github.pedrodimoura.news.articles.data.datasource.local.entity.ArticleLocal
import com.github.pedrodimoura.news.articles.data.datasource.local.entity.SourceLocal
import com.github.pedrodimoura.news.articles.data.datasource.mapper.asArticle
import com.github.pedrodimoura.news.articles.data.datasource.mapper.asArticleLocal
import com.github.pedrodimoura.news.articles.data.datasource.remote.entity.ArticleRemote
import com.github.pedrodimoura.news.articles.data.datasource.remote.entity.SourceRemote
import com.github.pedrodimoura.news.articles.domain.entity.Article
import com.github.pedrodimoura.news.articles.domain.entity.Source
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class Mappers : BaseTest() {

    override fun setup() {}

    override fun tearDown() {}

    @Test
    fun `Should asArticleLocal from Article return a valid ArticleLocal`() {
        val articleLocal = articleExpected.asArticleLocal()
        assertEquals(articleLocalExpected, articleLocal)
    }

    @Test
    fun `Should asArticle from ArticleLocal return a valid Article`() {
        val article = articleLocalExpected.asArticle()
        assertEquals(articleExpected, article)
    }

    @Test
    fun `Should asArticle from ArticleRemote return a valid Article`() {
        val article = articleRemote.asArticle()
        assertEquals(articleExpected, article)
    }

    @Test
    fun `Should asArticleLocal from ArticleRemote return a valid ArticleLocal`() {
        val article = articleRemote.asArticleLocal()
        assertEquals(articleLocalExpected, article)
    }

    companion object {
        @JvmStatic
        val date = Date()

        @JvmStatic
        val source = Source("name", "name")

        @JvmStatic
        val articleExpected = Article(
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
        val sourceLocal = SourceLocal("name")

        @JvmStatic
        val articleLocalExpected = ArticleLocal(
            0,
            sourceLocal,
            "author",
            "title",
            "description",
            "url",
            "urlToImage",
            date,
            "content"
        )

        @JvmStatic
        val sourceRemote = SourceRemote("name")

        @JvmStatic
        val articleRemote = ArticleRemote(
            sourceRemote,
            "author",
            "title",
            "description",
            "url",
            "urlToImage",
            date,
            "content"
        )

    }

}