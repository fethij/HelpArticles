@file:OptIn(ExperimentalTime::class)

package com.tewelde.articles.core.data.store

import com.tewelde.articles.core.database.model.ArticleEntity
import com.tewelde.articles.core.model.Article
import com.tewelde.articles.core.network.model.NetworkArticle
import com.tewelde.articles.core.network.model.NetworkArticleDetail
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

fun NetworkArticle.toDomainModel(): Article {
    return Article(
        id = id,
        title = title,
        summary = summary,
        updatedAt = updatedAt,
        category = category,
        cachedAt = Clock.System.now().toEpochMilliseconds()
    )
}

fun NetworkArticleDetail.toDomainModel(): Article {
    return Article(
        id = id,
        title = title,
        summary = summary,
        updatedAt = updatedAt,
        category = category,
        cachedAt = Clock.System.now().toEpochMilliseconds(),
        content = content
    )
}

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = id,
        title = title,
        summary = summary,
        category = category,
        updatedAt = updatedAt,
        cachedAt = cachedAt,
        content = content
    )
}