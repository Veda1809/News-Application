package com.example1.newsapplication.data.mappers

import com.example1.newsapplication.data.Articles
import com.example1.newsapplication.data.domain.ArticlesData
import com.example1.newsapplication.data.Source
import com.example1.newsapplication.data.domain.SourceData
import com.example1.newsapplication.data.domain.TotalNews
import com.example1.newsapplication.data.TotalNewsResponse

fun TotalNewsResponse.toTotalNews(): TotalNews {
    return TotalNews(
        status = status ?: "",
        totalResults = totalResults ?: 0,
        articles = articles?.map { it.toArticles() },
        code = code ?: "",
        message = message ?: ""
    )
}

fun Articles.toArticles(): ArticlesData {
    return ArticlesData(
        author = author ?: "",
        title = title ?: "",
        description = description ?: "",
        urlToImage = urlToImage ?: "",
        url = url ?: "",
        publishedAt = publishedAt ?: "",
        content = content ?: "",
        source = source?.toSource()
    )
}

fun Source.toSource(): SourceData {
    return SourceData(
        id = id ?: "",
        name = name ?: ""
    )
}