package com.runcode.news.di

import android.content.Context
import com.runcode.news.data.repository.Repository
import com.runcode.news.data.api.NewsApiCall
import com.runcode.news.data.api.model.BreakingNewsMapper
import com.runcode.news.data.api.model.HeadlinesMapper
import com.runcode.news.data.database.HeadlinesDao
import com.runcode.news.data.database.BreakingNewsDao
import com.runcode.news.data.database.model.BreakingNewsMapperDatabase
import com.runcode.news.data.database.model.HeadlinesMapperDatabase
import com.runcode.news.data.repository.CustomSearchRepositoryImpl
import com.runcode.news.data.repository.NewsListRepositoryImpl
import com.runcode.news.data.repository.SavedNewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun ProvidesNewsListRepository(
        api: NewsApiCall,
        breakingNewsDao: BreakingNewsDao,
        networkMapperBreakingNews: BreakingNewsMapper,
        databaseMapperBreakingNews: BreakingNewsMapperDatabase,
        @ApplicationContext context: Context

    ): NewsListRepositoryImpl {
        return NewsListRepositoryImpl(
            breakingNewsDao, api, networkMapperBreakingNews,
            databaseMapperBreakingNews, context
        )
    }

    @Provides
    fun ProvideRepository(
        @ApplicationContext context: Context,
        api: NewsApiCall,
        breakingNewsDao: BreakingNewsDao,
        headlinesDao: HeadlinesDao,
        networkMapperHeadlines: HeadlinesMapper,
        networkMapperBreakingNews: BreakingNewsMapper,
        databaseMapperBreakingNews: BreakingNewsMapperDatabase,
        databaseMapperHeadlines: HeadlinesMapperDatabase
    ): Repository {
        return Repository(
            context, api, breakingNewsDao,
            headlinesDao, networkMapperHeadlines,
            networkMapperBreakingNews,
            databaseMapperBreakingNews, databaseMapperHeadlines
        )
    }

    @Provides
    fun provideSavedNewsRepository(
        breakingNewsDao: BreakingNewsDao,
        databaseMapperBreakingNews: BreakingNewsMapperDatabase,
        @ApplicationContext context: Context
    ): SavedNewsRepositoryImpl {
        return SavedNewsRepositoryImpl(context, breakingNewsDao, databaseMapperBreakingNews)
    }

    @Provides
    fun provideCustomSearchRepository(
        api: NewsApiCall,
        mapper: BreakingNewsMapper
    ): CustomSearchRepositoryImpl =
        CustomSearchRepositoryImpl(api, mapper)
}








