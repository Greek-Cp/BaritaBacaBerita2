package com.yanuar.barita.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yanuar.barita.models.News
import com.yanuar.barita.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val _newsData = MutableLiveData<List<News>>()
    val newsData: LiveData<List<News>> = _newsData

    fun fetchLatestNews() {
        viewModelScope.launch {
            try {
                val response = newsRepository.getLatestNews()
                if (response.success) {
                    _newsData.postValue(response.data.posts)
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
