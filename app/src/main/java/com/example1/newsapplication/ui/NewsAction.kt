package com.example1.newsapplication.ui

sealed interface NewsAction{
    data class onNewsItemClick(val url: String) : NewsAction
    data class onCategoryItemClick(val category: String): NewsAction
    data class onSearchedClick(val keyword: String): NewsAction
    data object onSearchCleared: NewsAction
}
