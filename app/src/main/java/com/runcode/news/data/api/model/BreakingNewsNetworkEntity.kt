package com.runcode.news.data.api.model

data class BreakingNewsNetworkEntity(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)