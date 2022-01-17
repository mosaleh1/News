package com.runcode.news.data.api

import com.runcode.news.data.Constants
import com.runcode.news.data.api.model.BreakingNewsNetworkEntity
import com.runcode.news.data.api.model.HeadlineNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDateTime

interface NewsApiCall {
    @GET("v2/top-headlines")
    suspend fun getHeadLine(
        @Query("country") topic: String = "us",
        @Query("apiKey") apiKey: String = Constants.API_KEY,
        @Query("language") lang: String = "en",
        @Query("from") date: String = "3-12-2021"
    ): HeadlineNetworkEntity

    @GET("v2/everything")
    suspend fun getBreakingNews(
        @Query("q") topic: String = "latest",
        @Query("apiKey") apiKey: String = Constants.API_KEY,
        @Query("language") lang: String = "en",
        @Query("from") date: String = LocalDateTime.now().toString()
    ): BreakingNewsNetworkEntity

    @GET("v2/everything")
    suspend fun getNewsByTopic(
        @Query("q") topic: String,
        @Query("apiKey") apiKey: String = Constants.API_KEY,
        @Query("language") lang: String = "en",
        @Query("from") date: String = LocalDateTime.now().toString()
    ): BreakingNewsNetworkEntity



}