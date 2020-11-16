package com.example.news.api

import com.example.news.model.Channel
import com.example.news.model.Feed
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class NewsRetriever {
    private val service: NewsService

    companion object {
        const val BASE_URL = "https://www.eurosport.ru/"
    }

    init {
        val retrofit = Retrofit.Builder()
            // 1
            .baseUrl(BASE_URL)
            //.addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
        service = retrofit.create(NewsService::class.java)
    }

    suspend fun getNews(): Channel {
        return  service.retrieveNews().channel!!
    }

}