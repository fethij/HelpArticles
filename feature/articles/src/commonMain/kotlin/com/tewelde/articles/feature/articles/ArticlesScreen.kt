package com.tewelde.articles.feature.articles

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.slack.circuit.codegen.annotations.CircuitInject
import com.tewelde.articles.core.common.di.UiScope
import com.tewelde.articles.core.navigation.ArticlesScreen
import com.tewelde.articles.feature.articles.component.ArticlesFilterChip
import com.tewelde.articles.feature.articles.model.ArticlesEvent
import com.tewelde.articles.feature.articles.model.ArticlesLoadState
import com.tewelde.articles.feature.articles.model.ArticlesUiState
import com.tewelde.articles.resources.Res
import com.tewelde.articles.resources.no_arts
import org.jetbrains.compose.resources.stringResource

@CircuitInject(ArticlesScreen::class, UiScope::class)
@Composable
fun ArticlesScreen(
    uiState: ArticlesUiState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
    ) { contentPadding ->
        when (val state = uiState.articlesContentState) {
            ArticlesLoadState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is ArticlesLoadState.Empty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(Res.string.no_arts),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }

            is ArticlesLoadState.Error -> {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                ) {
                    Column(
                        modifier =
                            Modifier
                                .align(Alignment.Center)
                                .width(IntrinsicSize.Max),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement =
                            Arrangement.spacedBy(
                                24.dp,
                                Alignment.CenterVertically,
                            ),
                    ) {
                        Icon(
                            imageVector = state.errorIcon,
                            contentDescription = "Error",
                            modifier =
                                Modifier
                                    .size(48.dp),
                        )
                        Text(
                            text = state.message,
                            textAlign = TextAlign.Center,
                        )
                        Button(
                            onClick = {
                                state.eventSink(ArticlesEvent.Retry)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Retry")
                        }
                    }
                }
            }

            is ArticlesLoadState.Success -> {
                Column(
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize(),
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(
                            items = state.categories,
                            key = { category -> "key-$category" }
                        ) { category ->
                            ArticlesFilterChip(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                selected = state.filteredCategories.contains(category),
                                onSelectedChange = {
                                    uiState.eventSink(ArticlesEvent.CategoryFiltered(category))
                                },
                                label = {
                                    Text(
                                        text = category,
                                        fontSize = MaterialTheme.typography.labelMedium.fontSize
                                    )
                                },
                            )
                        }
                    }

                    val minGridSize = 175
                    val bgColor = MaterialTheme.colorScheme.background
//                    val gridState = rememberLazyStaggeredGridState()
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(minGridSize.dp),
//                        state = gridState,
                        contentPadding = PaddingValues(8.dp),
                        verticalItemSpacing = 8.dp,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(
                            items = state.filteredArticles,
                            key = { it.id },
                            contentType = { "article" }
                        ) { art ->
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .size(200.dp)
                                    .clickable {
                                        uiState.eventSink(ArticlesEvent.OnNavigateToDetail(art.id))
                                    }
                                    .background(
                                        color = bgColor,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .innerShadow(
                                        shape = RoundedCornerShape(20.dp),
                                        shadow = Shadow(
                                            radius = 10.dp,
                                            spread = 2.dp,
                                            color = Color(0x40000000),
                                            offset = DpOffset(x = 6.dp, 7.dp)
                                        )
                                    )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                ) {
                                    Text(
                                        text = art.title,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Text(
                                        text = art.summary,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}