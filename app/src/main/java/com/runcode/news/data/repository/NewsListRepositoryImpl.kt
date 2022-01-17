package com.runcode.news.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.runcode.news.data.api.NewsApiCall
import com.runcode.news.data.api.model.BreakingNewsMapper
import com.runcode.news.data.api.model.HeadlinesMapper
import com.runcode.news.data.database.BreakingNewsDao
import com.runcode.news.data.database.HeadlinesDao
import com.runcode.news.data.database.model.BreakingNewsMapperDatabase
import com.runcode.news.data.database.model.HeadlinesMapperDatabase
import com.runcode.news.data.model.BreakingNews
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private const val TAG = "NewsListRepositoryImpl"

class NewsListRepositoryImpl
@Inject constructor(
    private val breakingBreakingNewsDatabase: BreakingNewsDao,
    val api: NewsApiCall,
    private val networkMapperBreakingNews: BreakingNewsMapper,
    private val databaseMapperBreakingNews: BreakingNewsMapperDatabase,
    val context: Context
) : NewsListRepository {
    override suspend fun getNewsByTopicFromNetwork(
        topic: String,
        date: String
    ): List<BreakingNews> {
        return networkMapperBreakingNews.mapFromListOfArticlesToBreakingNews(
            api.getBreakingNews(topic = topic, date = date).articles
        )
    }

    override suspend fun getNewsByTopicFromDatabase(topic: String): List<BreakingNews> {
        return databaseMapperBreakingNews.fromListOfEntityToBreakingNewsList(
            breakingBreakingNewsDatabase.getAllNewsByTopic(topic)
        )
    }

    override suspend fun insertAllNewsByTopic(news: List<BreakingNews>, topic: String) {
        breakingBreakingNewsDatabase.insertNews(
            databaseMapperBreakingNews.fromBreakingNewsListToListOfEntity(
                news
            )
        )
    }

    override suspend fun deleteAllByTopic() {
        TODO("Not yet implemented")
    }

    fun getOldDateForNews(): String {
        var date = LocalDateTime.now()
        date = date.minusDays(3)
        return DateTimeFormatter.ofPattern("dd-mm-yyyy").format(date).toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun isOldData(): Boolean {
        val pref: SharedPreferences = context.getSharedPreferences("NewsAppShortData", 0)
        val time = pref.getString("time_news_list", LocalDateTime.now().toString())
        val date = LocalDateTime.parse(time)
        Log.d(TAG, "isOldData: old time is ${time.toString()}  \ndate is ${date.toString()}")
        var date2 = LocalDateTime.from(date)
        date2 = date2.plusHours(2)
        Log.d(TAG, "isOldData: new time  \ndate is $date2")
        Log.d(TAG, "isOldData: ${date.hour}:${date.minute} hour  ${date2.hour}:${date2.minute}")
        val isOldData = LocalDateTime.now().isAfter(date2)
        Log.d(TAG, "isOldData: $isOldData ")
        if (isOldData) {
            GlobalScope.launch {
                clearCache()
            }
        }
        return isOldData
    }

    private suspend fun clearCache() {
        breakingBreakingNewsDatabase.deleteAllInTopics()
    }
}