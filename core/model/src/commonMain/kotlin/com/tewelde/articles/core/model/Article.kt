package com.tewelde.articles.core.model

data class Article(
    val id: String,
    val title: String,
    val summary: String,
    val updatedAt: String,
    val category: String,
    val cachedAt: Long,
    val content: String? = null
)