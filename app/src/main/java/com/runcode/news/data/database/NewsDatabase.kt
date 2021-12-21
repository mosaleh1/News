package com.runcode.news.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.runcode.news.data.database.model.BreakingNewsDatabase
import com.runcode.news.data.database.model.HeadlinesDatabase
import com.runcode.news.data.model.Headlines
import com.runcode.news.data.model.BreakingNews

@Database(entities = [BreakingNewsDatabase::class, HeadlinesDatabase::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun HeadlinesDao(): HeadlinesDao

}