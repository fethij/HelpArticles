package com.tewelde.articles.core.database.model

import com.tewelde.articles.core.model.Article
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Serializable
data class ArticleEntity(
    val id: String, //should be  Uuid or Long
    val title: String,
    val summary: String,
    val content: String? = null,
    val category: String,
    val updatedAt: String, // should be Long
    val cachedAt: Long = Clock.System.now().toEpochMilliseconds()
)

fun ArticleEntity.asDomainModel(): Article = Article(
    id = id,
    title = title,
    summary = summary,
    category = category,
    updatedAt = updatedAt,
    cachedAt = cachedAt,
    content = content
)
