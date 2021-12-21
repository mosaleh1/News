package com.runcode.news.di

import android.content.Context
import androidx.room.Room
import com.runcode.news.data.Constants
import com.runcode.news.data.api.NewsApiCall
import com.runcode.news.data.database.HeadlinesDao
import com.runcode.news.data.database.NewsDao
import com.runcode.news.data.database.NewsDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppScopedModule {

    @Provides
    @Singleton
    fun provideRetrofitApi(): NewsApiCall {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiCall::class.java)
    }


    @Volatile
    private var INSTANCE: NewsDatabase? = null

    @Provides
    @Singleton
    fun getInstance(@ApplicationContext context: Context): NewsDatabase {
        synchronized(this) {
            var instance = INSTANCE
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    NewsDatabase::class.java,
                    "NewsDatabase"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return instance
        }
    }

    @Provides
    @Singleton
    fun provideHomeBreakingDao(
        @ApplicationContext context: Context,
        db: NewsDatabase
    ): NewsDao {
        return db.newsDao()
    }

    @Provides
    @Singleton
    fun provideHomeHeadlineDao(
        @ApplicationContext context: Context,
        db: NewsDatabase
    ): HeadlinesDao {
        return db.HeadlinesDao()
    }

}
