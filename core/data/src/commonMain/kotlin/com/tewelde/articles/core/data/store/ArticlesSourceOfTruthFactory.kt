package com.tewelde.articles.core.data.store

import com.tewelde.articles.core.database.ArticlesLocalDataSource
import com.tewelde.articles.core.database.model.ArticleEntity
import com.tewelde.articles.core.database.model.asDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import org.mobilenativefoundation.store.store5.SourceOfTruth

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesSourceOfTruthFactory(
    val db: ArticlesLocalDataSource
) {

    fun create(): SourceOfTruth<ArticlesStore.Operation, List<ArticleEntity>, ArticlesStore.Output> =
        SourceOfTruth.of(
            reader = { operation -> handleRead(operation) },
            writer = { operation, articles -> handleWrite(operation, articles) },
        )

    private fun handleRead(operation: ArticlesStore.Operation): Flow<ArticlesStore.Output> {
        require(operation is ArticlesStore.Operation.All || operation is ArticlesStore.Operation.Single)
        return when (operation) {
            is ArticlesStore.Operation.All -> readAll(operation.category)
            is ArticlesStore.Operation.Single -> readSingle(operation.articleId)
        }
    }

    private fun readAll(
        category: Category?
    ): Flow<ArticlesStore.Output.Collection> = db.collectAllArticles(category)
        .map { articles ->
            ArticlesStore.Output.Collection(
                articles.map { it.asDomainModel() }
            )
        }

    private fun readSingle(articleId: ArticleId): Flow<ArticlesStore.Output.Single> =
        db.getArticleById(articleId)
            .mapNotNull { it?.asDomainModel() }
            .map { ArticlesStore.Output.Single(it) }

    private suspend fun handleWrite(
        operation: ArticlesStore.Operation,
        articles: List<ArticleEntity>,
    ) = when (operation) {
        is ArticlesStore.Operation.All -> writeAll(articles)
        is ArticlesStore.Operation.Single -> writeSingle(articles.first())
    }

    private suspend fun writeAll(
        articles: List<ArticleEntity>,
    ) = articles.forEach { article ->
        db.insertArticle(article)
    }

    private suspend fun writeSingle(
        article: ArticleEntity,
    ) = db.insertArticle(article)
}
