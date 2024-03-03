package com.yanuar.barita.network

import com.yanuar.barita.models.News
import com.yanuar.barita.models.NewsResponse
import retrofit2.Call
import retrofit2.http.GET


interface NewsApiService {
    @GET("cnbc/terbaru/")
    suspend fun getLatestNews(): NewsResponse
}