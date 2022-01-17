package com.runcode.news.data.repository

import com.runcode.news.data.model.BreakingNews

interface SavedNewsRepository  {

    suspend fun getAllFavoriteNews():List<BreakingNews>



}