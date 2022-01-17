package com.runcode.news.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.runcode.news.data.database.model.HeadlinesDatabase
import com.runcode.news.data.model.Headlines

@Dao
interface HeadlinesDao {

    @Insert
    suspend fun insertAllHeadlines(headlines: List<HeadlinesDatabase>)

    @Query("delete from headlines")
    suspend fun clearCache()

    @Query("select * from headlines")
    suspend fun getAllFromDB(): List<HeadlinesDatabase>
}