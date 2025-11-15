package com.tewelde.articles.core.navigation

import com.slack.circuit.runtime.screen.Screen as CircuitScreen

@Parcelize
data object ArticlesScreen : Screen("articles")

@Parcelize
data class ArticleDetailScreen(
    val arrticleId: String,
) : Screen("article_detail") {

}

abstract class Screen(
    val name: String,
) : CircuitScreen {
    open val arguments: Map<String, *>? = null
}