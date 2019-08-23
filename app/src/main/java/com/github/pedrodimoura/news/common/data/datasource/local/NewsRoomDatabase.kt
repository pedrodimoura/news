package com.github.pedrodimoura.news.common.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.pedrodimoura.news.articles.data.datasource.local.ArticleDAO
import com.github.pedrodimoura.news.articles.data.datasource.local.entity.ArticleLocal
import com.github.pedrodimoura.news.articles.data.datasource.local.entity.SourceLocal
import com.github.pedrodimoura.news.common.data.datasource.local.converter.DateTypeConverter

@Database(
    entities = [ArticleLocal::class, SourceLocal::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTypeConverter::class)
abstract class NewsRoomDatabase : RoomDatabase() {

    companion object {
        const val NAME = "news_db"
    }

    abstract fun articleDAO(): ArticleDAO

}