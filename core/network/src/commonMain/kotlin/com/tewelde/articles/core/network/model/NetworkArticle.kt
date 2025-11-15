package com.tewelde.articles.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkArticle(
    val id: String,
    val title: String,
    val summary: String,
    val updatedAt: String,
    val category: String
)