package com.example1.newsapplication.ui

import androidx.annotation.Keep
import com.example1.newsapplication.data.domain.ArticlesData

@Keep
data class TotalNewsState(
    val articles: List<ArticlesData>? = emptyList(),
    val isLoading: Boolean = false
)

