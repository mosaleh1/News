package com.runcode.news.di

import com.runcode.news.data.api.model.BreakingNewsMapper
import com.runcode.news.data.api.model.HeadlinesMapper
import com.runcode.news.data.database.model.BreakingNewsMapperDatabase
import com.runcode.news.data.database.model.HeadlinesMapperDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Mappers {

    @Provides
    @Singleton
    fun provideBreakingNewsDataBaseMapper(): BreakingNewsMapperDatabase =
        BreakingNewsMapperDatabase()


    @Singleton
    @Provides
    fun provideHeadlinesDataBaseMapper(): HeadlinesMapperDatabase = HeadlinesMapperDatabase()


    @Provides
    fun provideBreakingNewsNetworkMapper(): BreakingNewsMapper = BreakingNewsMapper()


    @Provides
    fun provideNetworkHeadlines(): HeadlinesMapper = HeadlinesMapper()


}