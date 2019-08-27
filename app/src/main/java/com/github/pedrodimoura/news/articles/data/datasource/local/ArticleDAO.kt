package com.github.pedrodimoura.news.articles.data.datasource.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.pedrodimoura.news.articles.data.datasource.local.entity.ArticleLocal

@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(articleLocalList: List<ArticleLocal>)

    @Query("SELECT * FROM article")
    fun queryAll(): DataSource.Factory<Int, ArticleLocal>

    @Query("SELECT count(*) FROM article")
    suspend fun count(): Int

}