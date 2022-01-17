package com.runcode.news.screens.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runcode.news.data.model.BreakingNews
import com.runcode.news.data.model.intents.HomeIntent
import com.runcode.news.data.model.intents.SavedNewsIntent
import com.runcode.news.data.model.states.HomeStates
import com.runcode.news.data.model.states.SavedNewsStates
import com.runcode.news.data.repository.Repository
import com.runcode.news.data.repository.SavedNewsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    private val repository: SavedNewsRepositoryImpl,
    val intentsChannel:Channel<SavedNewsIntent>
) : ViewModel(){

    init {
        handleIntent()
    }


    private val _breakingNewsState = MutableStateFlow<SavedNewsStates>(SavedNewsStates.Idle)
    val breakingNewsState: StateFlow<SavedNewsStates>
        get() = _breakingNewsState

    private fun handleIntent(){
            viewModelScope.launch {
                intentsChannel.consumeAsFlow().collect {
                    when (it) {
                        is SavedNewsIntent.GetAllFavorite -> handleGetAllFavorites()
                    }
                }
            }
    }

    private fun handleGetAllFavorites() {
        viewModelScope.launch (Dispatchers.Main){
            _breakingNewsState.value = SavedNewsStates.Loading
            try {
                _breakingNewsState.value = SavedNewsStates.Success(getAllFavorite())
            }catch (e:Exception){
                _breakingNewsState.value = SavedNewsStates.Error(e.message)
            }
        }
    }


    private suspend fun getAllFavorite():List<BreakingNews>{
        val async = viewModelScope.async {   repository.getAllFavoriteNews()}
        return async.await()
    }


}