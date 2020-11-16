package com.example.news.api

import com.example.news.model.Feed
import retrofit2.http.GET

interface NewsService {
    @GET("rss.xml")
    suspend fun retrieveNews(): Feed
}