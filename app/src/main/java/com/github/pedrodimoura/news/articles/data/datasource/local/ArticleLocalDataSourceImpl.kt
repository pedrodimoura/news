package com.github.pedrodimoura.news.articles.data.datasource.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.github.pedrodimoura.news.articles.data.datasource.ArticleDataSource
import com.github.pedrodimoura.news.articles.data.datasource.local.entity.ArticleLocal

class ArticleLocalDataSourceImpl(
    private val articleDAO: ArticleDAO
) : ArticleDataSource<ArticleLocal> {

    override suspend fun fetchTopHeadlines(
        country: String,
        page: Int,
        pageSize: Int
    ): LiveData<List<ArticleLocal>> {
        return Transformations.map(articleDAO.queryAll()) { articles ->
            articles.map { article -> article }
        }
    }

    override suspend fun save(vararg articleData: ArticleLocal) =
        articleData.forEach { articleDAO.save(it) }
}