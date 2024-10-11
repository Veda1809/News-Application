package com.example1.newsapplication.data.domain

import androidx.annotation.Keep

@Keep
data class TotalNews(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticlesData>? = ArrayList(),
    val code: String,
    val message: String
)

@Keep
data class ArticlesData(
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val publishedAt: String = "",
    val content: String = "",
    val source: SourceData? = null
)

@Keep
data class SourceData(
    val id: String = "",
    val name: String = ""
)