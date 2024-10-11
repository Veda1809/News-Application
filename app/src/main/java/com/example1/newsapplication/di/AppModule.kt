package com.example1.newsapplication.di

import com.example1.newsapplication.data.remote.ApiService
import com.example1.newsapplication.utils.AppConstants.BASE_URL
import com.example1.newsapplication.utils.RetrofitUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        val client = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(logging)
        client.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        RetrofitUtils.createApiService<ApiService>(get())
    }
}

