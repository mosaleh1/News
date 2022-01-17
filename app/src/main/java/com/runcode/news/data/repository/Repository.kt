package com.runcode.news.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.runcode.news.data.Constants
import com.runcode.news.data.api.NewsApiCall
import com.runcode.news.data.api.model.BreakingNewsMapper
import com.runcode.news.data.api.model.HeadlinesMapper
import com.runcode.news.data.database.HeadlinesDao
import com.runcode.news.data.database.BreakingNewsDao
import com.runcode.news.data.database.model.BreakingNewsDatabase
import com.runcode.news.data.database.model.BreakingNewsMapperDatabase
import com.runcode.news.data.database.model.HeadlinesMapperDatabase
import com.runcode.news.data.model.BreakingNews
import com.runcode.news.data.model.BreakingNewsModel
import com.runcode.news.data.model.Headlines
import com.runcode.news.data.model.HeadlinesModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class Repository @Inject constructor(
    @ApplicationContext val context: Context,
    val api: NewsApiCall,
    private val breakingBreakingNewsDatabase: BreakingNewsDao,
    private val headlinesDatabase: HeadlinesDao,
    private val networkMapperHeadlines: HeadlinesMapper,
    private val networkMapperBreakingNews: BreakingNewsMapper,
    private val databaseMapperBreakingNews: BreakingNewsMapperDatabase,
    private val databaseMapperHeadlines: HeadlinesMapperDatabase
) {
    //from network

    /*home fragment related data*/


    suspend fun getHeadLine(date: String): HeadlinesModel {
        return networkMapperHeadlines.fromEntityToDomain(api.getHeadLine(date = date))
    }

    suspend fun getBreakingNews(date: String): BreakingNewsModel {
        return networkMapperBreakingNews.fromEntityToDomain(api.getBreakingNews(date = date))
    }

    /*search fragment related data*/
    suspend fun getNewsByTopic(topic: String, date: String): BreakingNewsModel {
        return networkMapperBreakingNews.fromEntityToDomain(api.getNewsByTopic(topic, date = date))
    }

    //from db

    /*home fragment related data <Breaking BreakingNews>*/
    suspend fun getAllNews(): List<BreakingNews> {
        return databaseMapperBreakingNews.fromListOfEntityToBreakingNewsList(breakingBreakingNewsDatabase.getAllNews())
    }

    suspend fun insertNews(breakingNews: List<BreakingNews>) {
        return breakingBreakingNewsDatabase.insertNews(
            databaseMapperBreakingNews.fromBreakingNewsListToListOfEntity(breakingNews)
        )
    }

    suspend fun deleteAll() {
        return breakingBreakingNewsDatabase.deleteAllBreakingNews()
    }

    /*home fragment related data <Headlines>*/

//        NewsDatabase.getInstance(context).HeadlinesDao()


    suspend fun insertAllHeadlines(headlines: List<Headlines>) {
        headlinesDatabase.insertAllHeadlines(
            databaseMapperHeadlines.fromHeadlinesListToListOfEntity(headlines)
        )
    }


    private suspend fun clearCache() {
        headlinesDatabase.clearCache()
        breakingBreakingNewsDatabase.deleteAllBreakingNews()
    }

    suspend fun getAllHeadlines(): List<Headlines> {
        return databaseMapperHeadlines.fromListOfEntityToHeadlinesList(headlinesDatabase.getAllFromDB())
    }

    private val TAG = "Repository"



    @DelicateCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.O)
    fun isOldData(): Boolean {
        val pref: SharedPreferences = context.getSharedPreferences("NewsAppShortData", 0)
        val time = pref.getString("time", LocalDateTime.now().toString())
        val date = LocalDateTime.parse(time)
        Log.d(TAG, "isOldData: old time is ${time.toString()}  \ndate is ${date.toString()}")
        var date2 = LocalDateTime.from(date)
        date2 = date2.plusHours(2)
        Log.d(TAG, "isOldData: new time  \ndate is $date2")
        Log.d(TAG, "isOldData: ${date.hour}:${date.minute} hour  ${date2.hour}:${date2.minute}")
        val isOldData = LocalDateTime.now().isAfter(date2)
        Log.d(TAG, "isOldData: $isOldData ")
        if(isOldData){
            GlobalScope.launch {
                clearCache()
            }
        }
        return isOldData
    }

    suspend fun notifyIsOldData (){
        clearCache()
    }

    fun notifyNewsFetched(){
        Log.d(TAG, "notifyNewsFetched: ")
        val local = LocalDateTime.now().toString()
        val pref = context.getSharedPreferences("NewsAppShortData", 0)
        val editor = pref.edit()
        editor.putString("time", local)
        editor.apply()
    }


    fun isFirstTime(): Boolean {
        val pref = context.getSharedPreferences(Constants.SHARED_PREF_NAME, 0)
        val value = pref.getBoolean("isFirstTime", true)
        pref.edit().putBoolean("isFirstTime", false).apply()
        return value
    }



    fun getRequestDate(): String {
        var localDateTime = LocalDateTime.now()
        localDateTime = localDateTime.minusDays(3)
        val dateFormatting = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return dateFormatting.format(localDateTime)
    }

    suspend fun saveToFavorite(breakingNews: BreakingNews) {
        val breakingNEwsDatabase = databaseMapperBreakingNews.fromDomainToEntity(breakingNews)
        breakingBreakingNewsDatabase.insertToFavorite(breakingNEwsDatabase)
    }
    suspend fun deleteFromFavorite(breakingNews: BreakingNews){
        val breakingNEwsDatabase = databaseMapperBreakingNews.fromDomainToEntity(breakingNews)
        breakingBreakingNewsDatabase.deleteFromFavorite(breakingNEwsDatabase)
    }

    suspend fun getAllFavorite():List<BreakingNewsDatabase>{
       return breakingBreakingNewsDatabase.getAllFavorite()
    }
}