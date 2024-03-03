package com.yanuar.barita.helper

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yanuar.barita.models.News
import com.yanuar.barita.models.User

class UserPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getUser(): User {
        val beritaDibaca = prefs.getInt("beritaDibaca", 0)
        val beritaDibagikan = prefs.getInt("beritaDibagikan", 0)
        val json = prefs.getString("listBeritaDisimpan", "")
        val type = object : TypeToken<List<News>>() {}.type
        val listBeritaDisimpan: List<News> = gson.fromJson(json, type) ?: emptyList()
        return User(beritaDibaca, beritaDibagikan, listBeritaDisimpan)
    }

    fun incrementBeritaDibaca() {
        val currentCount = prefs.getInt("beritaDibaca", 0)
        prefs.edit().putInt("beritaDibaca", currentCount + 1).apply()
    }

    fun incrementBeritaDibagikan() {
        val currentCount = prefs.getInt("beritaDibagikan", 0)
        prefs.edit().putInt("beritaDibagikan", currentCount + 1).apply()
    }

    fun addBeritaDisimpan(news: News, context: Context) {
        val json = prefs.getString("listBeritaDisimpan", "")
        val type = object : TypeToken<MutableList<News>>() {}.type
        val listBeritaDisimpan: MutableList<News> = gson.fromJson(json, type) ?: mutableListOf()
        val isDuplicate = listBeritaDisimpan.any { it.title == news.title } // Misalnya, Anda memeriksa judul berita
        if (!isDuplicate) {
            Toast.makeText(context, "Berita Berhasil Disimpan", Toast.LENGTH_SHORT).show()
            listBeritaDisimpan.add(news)
            prefs.edit().putString("listBeritaDisimpan", gson.toJson(listBeritaDisimpan)).apply()
        } else{
            Toast.makeText(context, "Berita Sudah Disimpan", Toast.LENGTH_SHORT).show()

        }
    }

}
