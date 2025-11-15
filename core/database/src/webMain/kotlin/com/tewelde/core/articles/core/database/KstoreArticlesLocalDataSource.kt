package com.tewelde.core.articles.core.database

import com.tewelde.articles.core.database.ArticlesLocalDataSource
import com.tewelde.articles.core.database.model.ArticleEntity
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class KstoreArticlesLocalDataSource() : ArticlesLocalDataSource {

    private val listStore: KStore<List<ArticleEntity>> = storeOf(
        key = "articles")


    override fun collectAllArticles(category: String?): Flow<List<ArticleEntity>> =
        listStore.updates.map { articles ->
            articles?.let {
                if (category != null) {
                    it.filter { article -> article.category == category }
                } else {
                    it
                }
            } ?: emptyList()
        }

    override fun getArticleById(articleId: String): Flow<ArticleEntity?> =
        listStore.updates.map { articles ->
            articles?.find { it.id == articleId }
        }

    override suspend fun insertArticle(article: ArticleEntity) {
        val currentArticles = listStore.get() ?: emptyList()
        val updatedArticles = currentArticles.filter { it.id != article.id } + article
        listStore.set(updatedArticles)
    }

    override suspend fun deleteArticle(articleId: String) {
        val currentArticles = listStore.get() ?: emptyList()
        val updatedArticles = currentArticles.filter { it.id != articleId }
        listStore.set(updatedArticles)
    }
}