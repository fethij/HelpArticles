package com.tewelde.articles.core.network

import com.tewelde.articles.core.network.model.NetworkArticle
import com.tewelde.articles.core.network.model.NetworkArticleDetail


interface ArticlesNetworkDataSource {
    suspend fun getArticles(category: String? = null): List<NetworkArticle>

    suspend fun getDetail(articleId: String): NetworkArticleDetail

}