package com.tewelde.feature.article.model

import com.slack.circuit.runtime.CircuitUiState
import com.tewelde.articles.core.model.Article

sealed interface ArticleDetailLoadState : CircuitUiState {
    data object Loading : ArticleDetailLoadState
    data class Error(val message: String) : ArticleDetailLoadState
    data class Success(
        val article: Article,
        val eventSink: (ArticleDetailEvent) -> Unit,
    ) : ArticleDetailLoadState
}

data class ArticlesUiState(
    val articleDetailLoadState: ArticleDetailLoadState,
    val eventSink: (ArticleDetailEvent) -> Unit,
) : CircuitUiState