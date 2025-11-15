package com.tewelde.feature.article.model

sealed class ArticleDetailEvent {
    data object OnNavigateBack : ArticleDetailEvent()
}