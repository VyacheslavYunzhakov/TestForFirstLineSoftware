package com.example.news.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.api.NewsRetriever
import com.example.news.model.Channel
import com.example.news.model.Feed

class NewsViewModel: ViewModel() {
    var newsList: MutableLiveData<Channel> = MutableLiveData()

    suspend fun init(){newsList.value = NewsRetriever().getNews()}
}