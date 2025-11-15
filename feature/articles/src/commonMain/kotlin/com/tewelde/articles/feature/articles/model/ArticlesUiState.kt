package com.tewelde.articles.feature.articles.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.slack.circuit.runtime.CircuitUiState
import com.tewelde.articles.core.model.Article

sealed interface ArticlesLoadState : CircuitUiState {
    data object Empty : ArticlesLoadState
    data object Loading : ArticlesLoadState
    data class Error(
        val message: String,
        val errorIcon: ImageVector,
        val eventSink: (ArticlesEvent) -> Unit,
    ) : ArticlesLoadState
    data class Success(
        val articles: List<Article>,
        val filteredCategories: List<String> = emptyList(),
        val eventSink: (ArticlesEvent) -> Unit,
    ) : ArticlesLoadState {
        val categories: List<String> = articles.map { it.category }.distinct()

        val filteredArticles: List<Article> = articles.filter { art ->
            filteredCategories.isEmpty() || filteredCategories.contains(art.category)
        }
    }
}

data class ArticlesUiState(
    val articlesContentState: ArticlesLoadState,
    val eventSink: (ArticlesEvent) -> Unit,
) : CircuitUiState