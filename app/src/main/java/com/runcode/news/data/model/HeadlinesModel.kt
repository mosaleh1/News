package com.runcode.news.data.model

import com.google.gson.annotations.SerializedName
import com.runcode.news.data.api.model.Article

data class HeadlinesModel(
    @SerializedName("articles")
    val articles: List<Headlines>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)
