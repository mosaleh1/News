package com.runcode.news.screens.custom_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runcode.news.data.model.BreakingNews
import com.runcode.news.data.model.intents.CustomSearchIntent
import com.runcode.news.data.model.states.CustomSearchStates
import com.runcode.news.data.model.states.SavedNewsStates
import com.runcode.news.data.repository.CustomSearchRepository
import com.runcode.news.data.repository.CustomSearchRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CustomSearchViewModel @Inject constructor(
    private val repository: CustomSearchRepositoryImpl,
    val intentsChannel: Channel<CustomSearchIntent>
) : ViewModel() {

    init {
        handleIntents()
    }

    private val _breakingNewsState = MutableStateFlow<CustomSearchStates>(CustomSearchStates.Idle)
    val breakingNewsState: StateFlow<CustomSearchStates>
        get() = _breakingNewsState

    private fun handleIntents() {
        viewModelScope.launch {
            intentsChannel.consumeAsFlow().collect {
                when (it) {
                    is CustomSearchIntent.GetNewsByQuery -> handleGetNewsByQuery(it.query)
                }
            }
        }
    }

    private fun handleGetNewsByQuery(query: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _breakingNewsState.value = CustomSearchStates.Loading
            try {
                val data = getNewsByTopic(query)
                if (data.isNotEmpty()) {
                    _breakingNewsState.value = CustomSearchStates.Success(data)
                } else {
                    throw Exception()
                }
            } catch (e: Exception) {
                _breakingNewsState.value = CustomSearchStates.Error(e.message)
            }
        }
    }

    private suspend fun getNewsByTopic(query: String): List<BreakingNews> {
        return repository.customSearch(query)
    }

}