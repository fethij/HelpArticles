package com.tewelde.articles.feature.articles

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.tewelde.articles.core.common.ArticlesAppError
import com.tewelde.articles.core.common.LoadState
import com.tewelde.articles.core.common.di.UiScope
import com.tewelde.articles.core.connectivity.NetworkMonitor
import com.tewelde.articles.core.domain.ObserveArticlesUseCase
import com.tewelde.articles.core.navigation.ArticleDetailScreen
import com.tewelde.articles.core.navigation.ArticlesScreen
import com.tewelde.articles.feature.articles.model.ArticlesEvent
import com.tewelde.articles.feature.articles.model.ArticlesLoadState
import com.tewelde.articles.feature.articles.model.ArticlesUiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
@CircuitInject(ArticlesScreen::class, UiScope::class)
class ArticlesPresenter(
    @Assisted val navigator: Navigator,
    private val observeArticles: ObserveArticlesUseCase,
    private val networkMonitor: NetworkMonitor
) : Presenter<ArticlesUiState> {
    @Composable
    override fun present(): ArticlesUiState {
        var filteredCategories: List<String> by rememberRetained { mutableStateOf(emptyList()) }
        var retryTrigger by rememberRetained { mutableStateOf(0) }

        val eventSink: (ArticlesEvent) -> Unit = { event ->
            when (event) {
                is ArticlesEvent.OnNavigateToDetail -> {
                    navigator.goTo(ArticleDetailScreen(event.articleId))
                }

                is ArticlesEvent.CategoryFiltered -> {
                    filteredCategories = if (filteredCategories.contains(event.category)) {
                        filteredCategories - event.category
                    } else {
                        filteredCategories + event.category
                    }
                }

                ArticlesEvent.Retry -> {
                    retryTrigger++
                }
            }
        }

        // auto-retry when connection returns if there's an error (no cache)
        val isConnected by networkMonitor.isConnected.collectAsState()
        var previouslyConnected by rememberRetained { mutableStateOf(isConnected) }
        val articlesLoadState by remember(retryTrigger) {
            observeArticles()
                .map { loadState ->
                    when (loadState) {
                        is LoadState.Loading -> ArticlesLoadState.Loading
                        is LoadState.Loaded -> {
                            val articles = loadState.data
                            if (articles.isEmpty()) {
                                ArticlesLoadState.Empty
                            } else {
                                ArticlesLoadState.Success(
                                    articles = articles,
                                    filteredCategories = emptyList(),
                                    eventSink = eventSink
                                )
                            }
                        }

                        is LoadState.Error -> {
                            // Use the specific error message and icon from AppError
                            ArticlesLoadState.Error(
                                message = loadState.error.message,
                                errorIcon = getErrorIcon(loadState.error),
                                eventSink = eventSink
                            )
                        }
                    }
                }
                .catch { throwable ->
                    val error = when (throwable) {
                        is ArticlesAppError -> throwable
                        else -> ArticlesAppError.UnknownError(throwable)
                    }
                    emit(
                        ArticlesLoadState.Error(
                            message = error.message,
                            errorIcon = getErrorIcon(error),
                            eventSink = eventSink
                        )
                    )
                }
        }.collectAsState(ArticlesLoadState.Loading)

        // Auto-retry when connectivity returns if we're in an error state (no cache available)
        LaunchedEffect(isConnected) {
            if (!previouslyConnected && isConnected && articlesLoadState is ArticlesLoadState.Error) {
                // Connectivity has returned and we're in an error state, automatically retry
                retryTrigger++
            }
            previouslyConnected = isConnected
        }

        val articlesContentState = when (val state = articlesLoadState) {
            is ArticlesLoadState.Success -> state.copy(filteredCategories = filteredCategories)
            else -> state
        }

        return ArticlesUiState(
            articlesContentState = articlesContentState,
            eventSink = eventSink,
        )
    }
}

/**
 * Maps an AppError to an appropriate icon for UI display.
 * - Connectivity errors use CloudOff icon
 * - Backend errors use Warning icon
 * - Other errors use Error icon
 */
private fun getErrorIcon(error: ArticlesAppError): ImageVector {
    return when (error) {
        is ArticlesAppError.ConnectivityError -> Icons.Default.CloudOff
        is ArticlesAppError.BackendErrorArticles -> Icons.Default.Warning
        is ArticlesAppError.SerializationError -> Icons.Default.Error
        is ArticlesAppError.UnknownError -> Icons.Default.Error
    }
}