package com.example1.newsapplication.utils

import retrofit2.Retrofit
import retrofit2.create

object RetrofitUtils {

    inline fun <reified T> createApiService(retrofit: Retrofit): T{
        return retrofit.create(T::class.java)
    }
}