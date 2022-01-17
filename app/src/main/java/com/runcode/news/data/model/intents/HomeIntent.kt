package com.runcode.news.data.model.intents

sealed class HomeIntent{

    object GetBreakingNews :HomeIntent()

    object GetHeadlines :HomeIntent()

}
