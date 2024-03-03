package com.yanuar.barita.network

import com.yanuar.barita.models.News
import com.yanuar.barita.models.Root
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


interface NewsApiService {
    @Headers(
        "X-Rapidapi-Key: c5e487b581msh1244340bf360b23p19d1fcjsn07e9f6d53e9f",
        "X-Rapidapi-Host: newsnow.p.rapidapi.com",
        "Content-Type: application/json"
    )
    @POST("/newsv2_top_news_location")
    suspend fun getTopNews(@Body request: NewsRequest): Root
}

data class NewsRequest(
    val location: String,
    val language: String,
    val page: Int
)
