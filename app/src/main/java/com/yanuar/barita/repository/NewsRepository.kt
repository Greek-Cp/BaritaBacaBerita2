package com.yanuar.barita.repository
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yanuar.barita.R
import com.yanuar.barita.models.News
import com.yanuar.barita.network.NewsApiService
import retrofit2.Call



class NewsRepository(private val context: Context) {
    fun getNews(page: Int, pageSize: Int = 300): MutableList<News> {
        val jsonDataString = context.resources.openRawResource(R.raw.news_data).bufferedReader().use { it.readText() }
        val itemType = object : TypeToken<List<News>>() {}.type
        val allNews: List<News> = Gson().fromJson(jsonDataString, itemType)
        val filteredNews = allNews.filter { it.text.length >= 80 }
        val startIndex = (page - 1) * pageSize
        val endIndex = minOf(startIndex + pageSize, filteredNews.size)
        return if (startIndex < filteredNews.size) ArrayList(filteredNews.subList(startIndex, endIndex)) else mutableListOf()
    }
}
