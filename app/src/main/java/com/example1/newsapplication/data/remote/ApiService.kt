package com.example1.newsapplication.data.remote

import com.example1.newsapplication.data.TotalNewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadLines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ):Response<TotalNewsResponse>

    @GET("v2/top-headlines")
    suspend fun getTopHeadlinesWithCategory(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Response<TotalNewsResponse>

    @GET("v2/everything")
    suspend fun getTotalNews(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String
    ): Response<TotalNewsResponse>
}