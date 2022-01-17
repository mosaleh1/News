package com.runcode.news.data.model


data class Headlines(
    val id: String,
    val name: String,
    val author: String,
    val title: String,
    val description: String,
    val content: String,
    val publishedAt: String,
    val urlToImage: String,
    val articleUrl: String
):News()