package com.example1.newsapplication.di

import com.example1.newsapplication.data.repository.NewsRepository
import com.example1.newsapplication.data.repository.NewsRepositoryImp
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::NewsRepositoryImp).bind<NewsRepository>()
}