package com.runcode.news.data.model.states

import com.runcode.news.data.model.News

sealed class HomeStates {

    object Idle : HomeStates()
    object Loading : HomeStates()
    data class Success(val news: List<News>) : HomeStates()
    data class Error(val message: String?) : HomeStates()
}