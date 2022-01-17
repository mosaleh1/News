package com.runcode.news.di

import com.runcode.news.data.model.intents.CustomSearchIntent
import com.runcode.news.data.model.intents.HomeIntent
import com.runcode.news.data.model.intents.SavedNewsIntent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.channels.Channel
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
object ViewModelScopedModule {

    @Provides
    @ViewModelScoped
    fun provideHomeChannel(): Channel<HomeIntent> = Channel(5)
    @Provides
    @ViewModelScoped
    fun provideSavedNewsChannel(): Channel<SavedNewsIntent> = Channel(5)
    @Provides
    @ViewModelScoped
    fun provideCustomSearchChannel(): Channel<CustomSearchIntent> = Channel(5)
}