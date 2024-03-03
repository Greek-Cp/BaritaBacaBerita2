package com.yanuar.barita.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


typealias Root = List<News>

@Parcelize
data class News(
    val title: String,
    @SerializedName("top_image")
    val topImage: String,
    val videos: List<String>,
    val url: String,
    val date: String,
    @SerializedName("short_description")
    val shortDescription: String,
    val text: String,
    val publisher: Publisher
) : Parcelable

@Parcelize
data class Publisher(
    val href: String,
    val title: String
) : Parcelable
