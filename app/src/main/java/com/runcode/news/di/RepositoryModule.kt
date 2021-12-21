package com.runcode.news.di

import android.content.Context
import com.runcode.news.data.repository.Repository
import com.runcode.news.data.api.NewsApiCall
import com.runcode.news.data.api.model.BreakingNewsMapper
import com.runcode.news.data.api.model.HeadlinesMapper
import com.runcode.news.data.database.HeadlinesDao
import com.runcode.news.data.database.NewsDao
import com.runcode.news.data.database.model.BreakingNewsMapperDatabase
import com.runcode.news.data.database.model.HeadlinesMapperDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun ProvideRepository(
        @ApplicationContext context: Context,
        api: NewsApiCall,
        newsDao: NewsDao,
        headlinesDao: HeadlinesDao,
        networkMapperHeadlines: HeadlinesMapper,
        networkMapperBreakingNews: BreakingNewsMapper,
        databaseMapperBreakingNews: BreakingNewsMapperDatabase,
        databaseMapperHeadlines: HeadlinesMapperDatabase
    ): Repository {
        return Repository(
            context, api, newsDao,
            headlinesDao, networkMapperHeadlines,
            networkMapperBreakingNews,
            databaseMapperBreakingNews, databaseMapperHeadlines
        )
    }
}