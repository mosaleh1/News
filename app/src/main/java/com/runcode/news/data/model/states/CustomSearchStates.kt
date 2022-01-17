package com.runcode.news.data.model.states

import com.runcode.news.data.model.BreakingNews

sealed class CustomSearchStates {
    object Idle : CustomSearchStates()
    object Loading : CustomSearchStates()
    data class Success(val news: List<BreakingNews>) : CustomSearchStates()
    data class Error(val message: String?) : CustomSearchStates()
}