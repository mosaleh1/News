package com.runcode.news.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity(tableName = "Headlines")
data class HeadlinesDatabase(
    val id: String,
    val name: String,
    val author: String,
    val title: String,
    val description: String,
    val content: String,
    val publishedAt: String,
    val urlToImage: String,
    @PrimaryKey
    @NotNull
    val articleUrl: String = ""
)