package com.yanuar.barita.repository

import com.yanuar.barita.models.NewsResponse
import com.yanuar.barita.network.NewsApiService
import retrofit2.Call

class NewsRepository(private val newsApiService: NewsApiService) {

    suspend fun getLatestNews(): NewsResponse = newsApiService.getLatestNews()
}