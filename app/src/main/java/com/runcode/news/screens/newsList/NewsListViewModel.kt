package com.runcode.news.screens.newsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runcode.news.data.repository.Repository
import com.runcode.news.data.model.BreakingNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(val repository: Repository) : ViewModel() {


    private var _errorLiveData = MutableLiveData<Boolean>()
    val errorLiveData: LiveData<Boolean> get() = _errorLiveData

    private val _getNewsByTopic = MutableLiveData<List<BreakingNews>>()
    val getBreakingNewsByTopic: LiveData<List<BreakingNews>>
        get() = _getNewsByTopic


    fun getNewsByTopic(topic: String) {
        viewModelScope.launch(Dispatchers.Main) {

            //get From DB
            //check if empty
            //get from api
            val data = getNewsByTopicFromNetwork(topic)
            //update ui
            _getNewsByTopic.value = data
            //insert to db

            //read from db

        }

    }

    private suspend fun getNewsByTopicFromNetwork(topic: String): List<BreakingNews> {
        val articles = viewModelScope.async(Dispatchers.IO) {
            repository.getNewsByTopic(topic, repository.getOldDateForNews()).articles
        }
        return articles.await()
    }
}