package com.runcode.news.data.repository

import com.runcode.news.data.model.BreakingNews

interface NewsListRepository {

    suspend fun getNewsByTopicFromNetwork(topic: String, date: String): List<BreakingNews>

    suspend fun getNewsByTopicFromDatabase(topic: String): List<BreakingNews>

    suspend fun insertAllNewsByTopic(news: List<BreakingNews>, topic: String)

    suspend fun deleteAllByTopic()

    fun isOldData(): Boolean
}