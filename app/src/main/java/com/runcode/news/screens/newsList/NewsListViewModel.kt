package com.runcode.news.screens.newsList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runcode.news.data.repository.Repository
import com.runcode.news.data.model.BreakingNews
import com.runcode.news.data.repository.NewsListRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "NewsListViewModel"
@HiltViewModel
class NewsListViewModel @Inject constructor(private val repository: NewsListRepositoryImpl) : ViewModel() {


    private var _errorLiveData = MutableLiveData<Boolean>()
    val errorLiveData: LiveData<Boolean> get() = _errorLiveData

    private val _getNewsByTopic = MutableLiveData<List<BreakingNews>>()
    val getBreakingNewsByTopic: LiveData<List<BreakingNews>>
        get() = _getNewsByTopic


    @DelicateCoroutinesApi
    fun getNewsByTopic(topic: String) {
        viewModelScope.launch(Dispatchers.Main) {
            //get From DB
            val list=getNewsByTopicFromDatabase(topic)
            //check if empty
            if (list.isEmpty() || repository.isOldData()){
                //get from api
                val data = getNewsByTopicFromNetwork(topic)
                //update ui
                _getNewsByTopic.value = data
                //insert to db
                insertToDatabaseByTopic(data,topic)
                //read from db
                Log.d(TAG, "getNewsByTopic: ${data.size}")
                _getNewsByTopic.value = data
            }else{
                _getNewsByTopic.value = list
            }
        }

    }

    private suspend fun getNewsByTopicFromNetwork(topic: String): List<BreakingNews> {
        Log.d(TAG, "getNewsByTopicFromNetwork: $topic ")
        val articles = viewModelScope.async(Dispatchers.IO) {
            repository.getNewsByTopicFromNetwork(topic,repository.getOldDateForNews())
        }
        return articles.await()
    }

    private suspend fun getNewsByTopicFromDatabase(topic: String): List<BreakingNews> {
        Log.d(TAG, "getNewsByTopicFromDatabase: $topic")
        val articles = viewModelScope.async(Dispatchers.IO) {
            repository.getNewsByTopicFromDatabase(topic)
        }
        return articles.await()
    }

    private suspend fun insertToDatabaseByTopic(news:List<BreakingNews>,topic: String){
        Log.d(TAG, "insertToDatabaseByTopic: $topic ")
        val articles = viewModelScope.async(Dispatchers.IO) {
            repository.insertAllNewsByTopic(news,topic)
        }
        articles.await()
    }


}