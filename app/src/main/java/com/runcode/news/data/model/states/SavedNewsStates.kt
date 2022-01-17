package com.runcode.news.data.model.states

import com.runcode.news.data.model.BreakingNews

sealed class SavedNewsStates{
    object Idle : SavedNewsStates()
    object Loading : SavedNewsStates()
    data class Success(val news: List<BreakingNews>) : SavedNewsStates()
    data class Error(val message: String?) : SavedNewsStates()
}