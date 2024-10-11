package com.example1.newsapplication.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TotalNewsResponse(
    @SerializedName ("status") val status: String? = null,
    @SerializedName ("totalResults") val totalResults: Int? = null,
    @SerializedName("articles") val articles: List<Articles>? = ArrayList(),
    @SerializedName("code") val code: String? = null,
    @SerializedName("message") val message: String? = null
)

@Keep
data class Articles(
    @SerializedName("source") val source: Source ? = null,
    @SerializedName ("author") val author: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("urlToImage") val urlToImage: String? = null,
    @SerializedName("publishedAt") val publishedAt: String? = null,
    @SerializedName("content") val content: String? = null
)

@Keep
data class Source(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null
)
