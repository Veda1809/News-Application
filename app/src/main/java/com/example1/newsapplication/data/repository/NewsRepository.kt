package com.example1.newsapplication.data.repository

import com.example1.newsapplication.data.domain.TotalNews

interface NewsRepository {

    suspend fun getTopHeadLines(country: String, apiKey: String) : TotalNews?

    suspend fun getTotalHeadLinesWithCategory(country: String, category: String, apiKey: String): TotalNews?

    suspend fun getTotalNews(keyword: String, apiKey: String): TotalNews?
}