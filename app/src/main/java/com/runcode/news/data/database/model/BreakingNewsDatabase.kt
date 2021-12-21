package com.runcode.news.data.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull


@Entity(tableName = "Breaking_News")
@Parcelize
data class BreakingNewsDatabase(
    val id: String?,
    val name: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val content: String?,
    val publishedAt: String?,
    val urlToImage: String?,
    @PrimaryKey
    @NotNull
    val articleUrl: String
) : Parcelable

//val id: Int,
//val name: String?,
//val author: String?,
//val title: String?,
//val description: String?,
//val content: String?,
//val publishedAt: String?,
//val urlToImage: String?,
//@PrimaryKey
//@SerializedName("url")
//val articleUrl: String =""