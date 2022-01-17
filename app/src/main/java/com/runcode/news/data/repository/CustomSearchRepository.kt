package com.runcode.news.data.repository

import com.runcode.news.data.model.BreakingNews

interface CustomSearchRepository  {

    suspend fun customSearch (query : String) : List<BreakingNews>

}