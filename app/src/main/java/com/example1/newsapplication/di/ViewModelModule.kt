package com.example1.newsapplication.di

import com.example1.newsapplication.ui.NewsScreenViewModule
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::NewsScreenViewModule)
}