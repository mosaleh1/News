package com.runcode.news.data.repository

import android.content.Context
import com.runcode.news.data.api.NewsApiCall
import com.runcode.news.data.api.model.BreakingNewsMapper
import com.runcode.news.data.database.BreakingNewsDao
import com.runcode.news.data.database.model.BreakingNewsMapperDatabase
import com.runcode.news.data.model.BreakingNews
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SavedNewsRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val breakingNewsDatabase: BreakingNewsDao,
    private val breakingNewsMapperDatabase: BreakingNewsMapperDatabase
) : SavedNewsRepository {

    override suspend fun getAllFavoriteNews():List<BreakingNews> {
        return breakingNewsMapperDatabase.fromListOfEntityToBreakingNewsList(
            breakingNewsDatabase.getAllFavorite()
        )
    }
}