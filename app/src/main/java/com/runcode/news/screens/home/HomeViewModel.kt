package com.runcode.news.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runcode.news.data.model.BreakingNews
import com.runcode.news.data.model.Headlines
import com.runcode.news.data.model.intents.HomeIntent
import com.runcode.news.data.model.states.HomeStates
import com.runcode.news.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import javax.inject.Inject


private const val TAG = "HomeViewModel"

@InternalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    val intentsChannel: Channel<HomeIntent>
) :
    ViewModel() {

    init {
        handleIntent()
    }

    private val _headlinesState = MutableStateFlow<HomeStates>(HomeStates.Idle)
    val headlinesState: StateFlow<HomeStates>
        get() = _headlinesState

    private val _breakingNewsState = MutableStateFlow<HomeStates>(HomeStates.Idle)
    val breakingNewsState: StateFlow<HomeStates>
        get() = _breakingNewsState

    @InternalCoroutinesApi
    private fun handleIntent() {
        viewModelScope.launch {
            intentsChannel.consumeAsFlow().collect {
                when (it) {
                    is HomeIntent.GetHeadlines -> handleGetHeadlines()
                    is HomeIntent.GetBreakingNews -> handleGetBreakingNews()
                }
            }
        }
    }

    private fun handleGetBreakingNews() {
        viewModelScope.launch {
            _breakingNewsState.value = HomeStates.Loading
            try {
                val success = HomeStates.Success(getBreakingNews())
                _breakingNewsState.value = success
            } catch (e: Exception) {
                _breakingNewsState.value = HomeStates.Error(e.message)
            }
        }
    }

    @DelicateCoroutinesApi
    @InternalCoroutinesApi
    private suspend fun handleGetHeadlines() {
        Log.d(TAG, "getHeadLine: is called")
        viewModelScope.launch {
            _headlinesState.value = HomeStates.Loading
            try {
                _headlinesState.value = HomeStates.Success(getHeadlines())
            } catch (e: Exception) {
                _headlinesState.value = HomeStates.Error(e.message)
            }
        }
    }


    @DelicateCoroutinesApi
    private suspend fun getHeadlines(): List<Headlines> {
        //getFrom DB
        val getHeadlineFromDB = viewModelScope.async { getHeadLinesFromDB() }
        val headlines = getHeadlineFromDB.await()

        val isOldData = repository.isOldData()
        return if (headlines.isEmpty() || isOldData) {
            if (isOldData) {
                repository.notifyIsOldData()
            }
            //get from api
            val getHeadlineFromApi = viewModelScope.async { getHeadLinesFromNetwork() }
            val data = getHeadlineFromApi.await()
            //insert to db
            insertHeadlinesToDB(data)
            //notify data fetched
            repository.notifyNewsFetched()
            //send to ui
            data
        } else {
            //send To UI
            headlines
        }
    }

    private suspend fun getBreakingNews(): List<BreakingNews> {
        //getFrom DB
        val getBreakingNewsFromDB = viewModelScope.async { getBreakingNewsFromDB() }
        val breakingNews = getBreakingNewsFromDB.await()
        Log.d(TAG, "getBreakingNews: ${breakingNews.size}")
        return if (breakingNews.isEmpty() || repository.isOldData()) {
            //get from api
            val getBreakingNewsFromNetwork =
                viewModelScope.async { getBreakingNewsFromNetwork() }
            val data = getBreakingNewsFromNetwork.await()
            //insert to db
            insertBreakingNewsToDB(data)
            //store the date of data to compare later
            repository.notifyNewsFetched()
            //send to ui
            data
        } else {
            //send To UI
            breakingNews
        }
    }

    private suspend fun getBreakingNewsFromNetwork(): List<BreakingNews> {
        Log.d(TAG, "getBreakingNewsFromNetwork: ")
        val async = viewModelScope.async(Dispatchers.IO) {
            val date = repository.getRequestDate()
            repository.getBreakingNews(date = date).articles
        }
        return async.await()
    }

    private suspend fun getHeadLinesFromNetwork(): List<Headlines> {
        Log.d(TAG, "getHeadLinesFromNetwork: ")
        val async = viewModelScope.async(Dispatchers.IO) {
            val date = repository.getRequestDate()
            repository.getHeadLine(date).articles
        }
        return async.await()
    }

    @InternalCoroutinesApi
    suspend fun getBreakingNewsFromDB(): List<BreakingNews> {
        Log.d(TAG, "getBreakingNewsFromDB: ")
        val async = viewModelScope.async(Dispatchers.IO) {
            repository.getAllNews()
        }
        return async.await()
    }

    private suspend fun getHeadLinesFromDB(): List<Headlines> {
        Log.d(TAG, "getHeadLinesFromDB: ")
        val async = viewModelScope.async(Dispatchers.IO) {
            repository.getAllHeadlines()
        }
        return async.await()
    }

    private suspend fun insertBreakingNewsToDB(breakingNews: List<BreakingNews>) {
        Log.d(TAG, "insertBreakingNewsToDB: ")
        viewModelScope.launch(Dispatchers.IO) { repository.insertNews(breakingNews) }
    }

    private suspend fun insertHeadlinesToDB(news: List<Headlines>) {
        Log.d(TAG, "insertHeadlinesToDB: ")
        viewModelScope.launch(Dispatchers.IO) { repository.insertAllHeadlines(news) }
    }

    @DelicateCoroutinesApi
    fun saveToFavorite(breakingNews: BreakingNews) {
        GlobalScope.launch(Dispatchers.IO) {
            repository.saveToFavorite(breakingNews)
            //handleGetBreakingNews()
        }
    }

    @DelicateCoroutinesApi
    fun deleteFromFavorite(breakingNews: BreakingNews) {
        GlobalScope.launch {
            repository.deleteFromFavorite(breakingNews)
        }
    }

    fun saveAll(breakingNews: List<BreakingNews>) {
        viewModelScope.launch(Dispatchers.IO) {
            _breakingNewsState.emit(HomeStates.Success(breakingNews))
            repository.insertNews(breakingNews)
        }
    }
}