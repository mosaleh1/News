package com.runcode.news.data.api.model

data class HeadlineNetworkEntity(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)