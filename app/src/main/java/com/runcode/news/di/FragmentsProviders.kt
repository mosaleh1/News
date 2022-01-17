package com.runcode.news.di

import android.content.Context
import com.runcode.news.screens.home.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(FragmentComponent::class)
@Module
object FragmentsProviders {


    @Provides
    fun provideNewsAdapter(@ApplicationContext context: Context): NewsAdapter = NewsAdapter(context)

}