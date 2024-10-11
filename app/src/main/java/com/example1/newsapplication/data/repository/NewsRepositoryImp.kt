package com.example1.newsapplication.data.repository

import com.example1.newsapplication.data.domain.TotalNews
import com.example1.newsapplication.data.remote.ApiService
import com.example1.newsapplication.data.mappers.toTotalNews

class NewsRepositoryImp(
    private val apiService: ApiService
) : NewsRepository{
    override suspend fun getTopHeadLines(country: String, apiKey: String): TotalNews? {
        val response = apiService.getTopHeadLines(country = country, apiKey = apiKey)
        return if(response.isSuccessful) {
            response.body()?.toTotalNews()
        } else {
            null
        }
    }

    override suspend fun getTotalHeadLinesWithCategory(
        country: String,
        category: String,
        apiKey: String
    ): TotalNews? {
        val response = apiService.getTopHeadlinesWithCategory(country = country, category = category, apiKey = apiKey)
        return if(response.isSuccessful){
            response.body()?.toTotalNews()
        } else {
            null
        }
    }

    override suspend fun getTotalNews(keyword: String, apiKey: String): TotalNews? {
        val response = apiService.getTotalNews(q = keyword, apiKey = apiKey)
        return if(response.isSuccessful){
            response.body()?.toTotalNews()
        } else {
            null
        }
    }
}