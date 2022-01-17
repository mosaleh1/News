package com.runcode.news.data.model

data class BreakingNewsModel(
    val articles: List<BreakingNews>,
    val status: String,
    val totalResults: Int
)
