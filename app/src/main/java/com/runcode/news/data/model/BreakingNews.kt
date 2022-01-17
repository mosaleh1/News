package com.runcode.news.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class BreakingNews (
    val id: String,
    val name: String,
    val author: String,
    val title: String,
    val description: String,
    val content: String,
    val publishedAt: String,
    val urlToImage: String,
    val articleUrl: String,
    val topic:String="BreakingNews",
    var isFavorite:Boolean = false
) : News(),Parcelable