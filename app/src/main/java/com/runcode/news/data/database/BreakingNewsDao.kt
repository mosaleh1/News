package com.runcode.news.data.database

import androidx.room.*
import com.runcode.news.data.database.model.BreakingNewsDatabase
import com.runcode.news.data.model.BreakingNews

@Dao
interface BreakingNewsDao {

    //home Fragment (Normal operations)

    @Query("select * from breaking_news where topic='BreakingNews'")
    suspend fun getAllNews(): List<BreakingNewsDatabase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(breakingNews: List<BreakingNewsDatabase>)

    @Query("delete from breaking_news where topic ='BreakingNews' & isFavorite == 0")
    suspend fun deleteAllBreakingNews()

    @Query("delete from breaking_news where topic!='BreakingNews'")
    suspend fun deleteAllInTopics()

    // by topic fragment
    @Query("select * from breaking_news where topic=:topic_param")
    suspend fun getAllNewsByTopic(topic_param: String): List<BreakingNewsDatabase>

    @Query("delete from breaking_news where topic=:topic_param")
    suspend fun deleteAllByTopic(topic_param:String)


    //favorite fragment operations
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToFavorite(breakingNews:BreakingNewsDatabase)

    @Query("select * from breaking_news where isFavorite == 1==1")
    suspend fun getAllFavorite():List<BreakingNewsDatabase>

    @Delete
    suspend fun deleteFromFavorite(breakingNews: BreakingNewsDatabase)
}