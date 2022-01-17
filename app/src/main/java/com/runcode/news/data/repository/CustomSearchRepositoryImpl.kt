package com.runcode.news.data.repository

import android.util.Log
import com.runcode.news.data.api.NewsApiCall
import com.runcode.news.data.api.model.BreakingNewsMapper
import com.runcode.news.data.model.BreakingNews
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val TAG = "CustomSearchRepositoryI"

class CustomSearchRepositoryImpl(
    private val api: NewsApiCall,
    private val mapper: BreakingNewsMapper
) : CustomSearchRepository {

    override suspend fun customSearch(query: String): List<BreakingNews> {
        val data = api.getNewsByTopic(topic = query, date = getOldDateForNews()).articles
        Log.d(TAG, "customSearch: ${data.size}")
        return mapper.mapFromListOfArticlesToBreakingNews(data)
    }

    private fun getOldDateForNews(): String {
        var date = LocalDateTime.now()
        date = date.minusDays(3)
        return DateTimeFormatter.ofPattern("dd-mm-yyyy").format(date).toString()
    }
}