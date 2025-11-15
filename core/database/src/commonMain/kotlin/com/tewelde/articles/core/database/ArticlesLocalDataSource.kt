package com.tewelde.articles.core.database

import com.tewelde.articles.core.database.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ArticlesLocalDataSource {
    fun collectAllArticles(category: String? = null): Flow<List<ArticleEntity>>
    fun getArticleById(articleId: String): Flow<ArticleEntity?>
    suspend fun insertArticle(article: ArticleEntity)
    suspend fun deleteArticle(articleId: String)
}