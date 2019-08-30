package com.github.pedrodimoura.news.articles.data.datasource.remote

import com.github.pedrodimoura.news.articles.data.datasource.remote.entity.TopHeadlinesRemote
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleService {

    @GET("top-headlines")
    suspend fun fetchTopHeadlines(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 21
    ): TopHeadlinesRemote

}