package com.tewelde.articles.core.database.room

import com.tewelde.articles.core.database.ArticlesDao
import com.tewelde.articles.core.database.ArticlesLocalDataSource
import com.tewelde.articles.core.database.RealArticleEntity
import com.tewelde.articles.core.database.asArticleEntity
import com.tewelde.articles.core.database.model.ArticleEntity
import com.tewelde.articles.core.database.toRealArticleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class RoomArticlesLocalDataSource(
    val dao: ArticlesDao
) : ArticlesLocalDataSource {
    override fun collectAllArticles(
        category: String?
    ): Flow<List<ArticleEntity>> = dao.getAllArticles(category).map {
        it.map(RealArticleEntity::asArticleEntity)
    }

    override fun getArticleById(articleId: String): Flow<ArticleEntity?> =
        dao.getArticleById(articleId).map { it?.asArticleEntity() }

    override suspend fun insertArticle(article: ArticleEntity) =
        dao.insertArticle(article.toRealArticleEntity())

    override suspend fun deleteArticle(articleId: String) =
        dao.deleteArticle(articleId)
}