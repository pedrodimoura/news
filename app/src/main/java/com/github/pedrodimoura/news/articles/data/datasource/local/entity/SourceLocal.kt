package com.github.pedrodimoura.news.articles.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source")
data class SourceLocal(@PrimaryKey(autoGenerate = false) val name: String = "")