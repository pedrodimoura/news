package com.github.pedrodimoura.news.articles.data.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.pedrodimoura.news.articles.data.datasource.local.entity.ArticleLocal

@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(articleLocal: ArticleLocal)

    @Query("SELECT * FROM article")
    fun queryAll(): LiveData<List<ArticleLocal>>

}