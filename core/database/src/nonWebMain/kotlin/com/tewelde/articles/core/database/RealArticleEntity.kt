package com.tewelde.articles.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tewelde.articles.core.database.model.ArticleEntity

@Entity(
    tableName = "articles"
)
data class RealArticleEntity(
    @PrimaryKey
    val id: String, // Uuid or Long
    val title: String,
    val summary: String,
    val content: String? = null, // Loaded lazily
    val category: String,
    val updatedAt: String, // Long
    val cachedAt: Long,
)

fun RealArticleEntity.asArticleEntity() = ArticleEntity(
    id = id,
    title = title,
    summary = summary,
    content = content,
    category = category,
    updatedAt = updatedAt,
    cachedAt = cachedAt,
)

fun ArticleEntity.toRealArticleEntity() = RealArticleEntity(
    id = id,
    title = title,
    summary = summary,
    content = content,
    category = category,
    updatedAt = updatedAt,
    cachedAt = cachedAt,
)
