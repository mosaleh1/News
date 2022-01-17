package com.runcode.news.data.model.intents

sealed class SavedNewsIntent {
    object GetAllFavorite : SavedNewsIntent()
}