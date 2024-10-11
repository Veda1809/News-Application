package com.example1.newsapplication.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example1.newsapplication.data.repository.NewsRepository
import com.example1.newsapplication.utils.AppConstants
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NewsScreenViewModule(
    private val repository: NewsRepository
): ViewModel() {

    var state by mutableStateOf(TotalNewsState())
        private set

    private var eventChannel = Channel<NewsEvent>()
    val event = eventChannel.receiveAsFlow()

    init {
        getTopHeadLines()
    }

    fun onAction(action: NewsAction){
        viewModelScope.launch {
            when(action){
                is NewsAction.onNewsItemClick -> {
                    eventChannel.send(NewsEvent.OpenUrl(action.url))
                }

                is NewsAction.onCategoryItemClick -> {
                    getTotalNewsBasedOnCategory(action.category)
                }

                is NewsAction.onSearchedClick -> {
                    getTotalNews(action.keyword)
                }

                NewsAction.onSearchCleared -> {
                    getTopHeadLines()
                }
            }
        }
    }

    private fun getTopHeadLines(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.getTopHeadLines(country = AppConstants.COUNTRY, apiKey = AppConstants.API_KEY)
            delay(2000L)
            state = state.copy(isLoading = false)
            if(result?.status == "ok"){
                result.let {
                    state = state.copy(
                        articles = result.articles
                    )
                }
            }

        }
    }

    private fun getTotalNewsBasedOnCategory(category: String){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.getTotalHeadLinesWithCategory(category = category, apiKey = AppConstants.API_KEY, country = AppConstants.COUNTRY)
            delay(2000L)
            state = state.copy(isLoading = false)
            if(result?.status == "ok"){
                result.let {
                    state = state.copy(
                        articles = result.articles
                    )
                }
            }
        }
    }

    private fun getTotalNews(keyword: String){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.getTotalNews(keyword = keyword, apiKey = AppConstants.API_KEY)
            delay(2000L)
            state = state.copy(isLoading = false)
            if(result?.status == "ok"){
                result.let {
                    state = state.copy(
                        articles = result.articles
                    )
                }
            } else if(result?.status == "error"){
                result.let {

                }
            }
        }
    }

}