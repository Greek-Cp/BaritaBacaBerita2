package com.yanuar.barita.models

data class NewsResponse(
    val success: Boolean,
    val message: String?,
    val data: NewsData
)


data class NewsData(
    val link: String,
    val description: String,
    val title: String,
    val image: String,
    val posts: List<News>
)

data class News(
    val link: String,
    val title: String,
    val pubDate: String,
    val description: String,
    val thumbnail: String
)
