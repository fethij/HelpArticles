package com.tewelde.feature.article

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.model.rememberMarkdownState
import com.slack.circuit.codegen.annotations.CircuitInject
import com.tewelde.articles.core.common.di.UiScope
import com.tewelde.articles.core.navigation.ArticleDetailScreen
import com.tewelde.feature.article.model.ArticleDetailEvent
import com.tewelde.feature.article.model.ArticleDetailLoadState
import com.tewelde.feature.article.model.ArticlesUiState

@CircuitInject(ArticleDetailScreen::class, UiScope::class)
@Composable
fun ArticleDetailScreen(
    uiState: ArticlesUiState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            BackButton(
                modifier = Modifier.statusBarsPadding()
            ) { uiState.eventSink(ArticleDetailEvent.OnNavigateBack) }
        }
    ) { contentPadding ->
        Box(Modifier.padding(contentPadding)) {
            when (val state = uiState.articleDetailLoadState) {
                ArticleDetailLoadState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ArticleDetailLoadState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = state.message,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                }

                is ArticleDetailLoadState.Success -> {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(32.dp),
                    ) {
                        Column {
                            Text(
                                text = state.article.title,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = state.article.summary,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        state.article.content?.let {
                            val markdownState = rememberMarkdownState(it)
                            Markdown(markdownState)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceFiltered(x0: String) {
    TODO("Not yet implemented")
}