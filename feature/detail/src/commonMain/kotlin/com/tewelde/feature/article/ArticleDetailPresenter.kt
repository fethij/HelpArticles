package com.tewelde.feature.article

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.tewelde.articles.core.common.ArticlesAppError
import com.tewelde.articles.core.common.LoadState
import com.tewelde.articles.core.common.di.UiScope
import com.tewelde.articles.core.domain.ObserveArticleDetailUseCase
import com.tewelde.articles.core.navigation.ArticleDetailScreen
import com.tewelde.feature.article.model.ArticleDetailEvent
import com.tewelde.feature.article.model.ArticleDetailLoadState
import com.tewelde.feature.article.model.ArticlesUiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
@CircuitInject(ArticleDetailScreen::class, UiScope::class)
class ArticleDetailPresenter(
    @Assisted val navigator: Navigator,
    @Assisted val screen: ArticleDetailScreen,
    private val observeArticle: ObserveArticleDetailUseCase,
) : Presenter<ArticlesUiState> {
    @Composable
    override fun present(): ArticlesUiState {
        val eventSink: (ArticleDetailEvent) -> Unit = { event ->
            when (event) {
                is ArticleDetailEvent.OnNavigateBack -> {
                    navigator.pop()
                }
            }
        }
        val articleDetailLoadState by remember {
            observeArticle(screen.arrticleId)
                .map { loadState ->
                    when (loadState) {
                        is LoadState.Loading -> ArticleDetailLoadState.Loading
                        is LoadState.Loaded -> {
                            val article = loadState.data
                            ArticleDetailLoadState.Success(
                                article = article,
                                eventSink = eventSink,
                            )
                        }

                        is LoadState.Error -> {
                            // Use the specific error message from AppError
                            ArticleDetailLoadState.Error(loadState.error.message)
                        }
                    }
                }
                .catch { throwable ->
                    val error = when (throwable) {
                        is ArticlesAppError -> throwable
                        else -> ArticlesAppError.UnknownError(throwable)
                    }
                    emit(ArticleDetailLoadState.Error(error.message))
                }
        }.collectAsState(ArticleDetailLoadState.Loading)

        return ArticlesUiState(
            articleDetailLoadState = articleDetailLoadState,
            eventSink = eventSink,
        )
    }
}