package com.example1.newsapplication.ui

sealed interface NewsEvent {
    data class OpenUrl(val url: String): NewsEvent
}