package com.runcode.news.data.model.intents

sealed class CustomSearchIntent {
    class GetNewsByQuery(val query: String) : CustomSearchIntent()
}