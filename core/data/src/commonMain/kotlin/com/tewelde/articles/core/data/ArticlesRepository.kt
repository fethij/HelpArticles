package com.tewelde.articles.core.data

import com.tewelde.articles.core.data.store.ArticlesStore
import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.StoreReadResponse

interface ArticlesRepository {
    /**
     * Observes a list of articles, optionally filtered by category ID
     */
    fun observeArticles(categoryId: String? = null): Flow<StoreReadResponse<ArticlesStore.Output>>

    /**
     * Observes a single article by its ID
     */
    fun observeArticle(articleId: String): Flow<StoreReadResponse<ArticlesStore.Output>>

    /**
     * Synchronizes articles by fetching fresh data from the network
     */
    suspend fun sync()
}