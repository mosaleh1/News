package com.runcode.news.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.runcode.news.data.database.model.BreakingNewsDatabase
import com.runcode.news.data.model.BreakingNews

@Dao
interface NewsDao {

    @Query("select * from breaking_news")
    suspend fun getAllNews(): List<BreakingNewsDatabase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(breakingNews: List<BreakingNewsDatabase>)

    @Query("delete from breaking_news")
    suspend fun deleteAll()


}