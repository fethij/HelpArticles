package com.tewelde.articles.feature.articles.model

sealed class ArticlesEvent {
    data class OnNavigateToDetail(val articleId: String): ArticlesEvent()
    data object Retry: ArticlesEvent()
    data class CategoryFiltered(val category: String): ArticlesEvent()
}