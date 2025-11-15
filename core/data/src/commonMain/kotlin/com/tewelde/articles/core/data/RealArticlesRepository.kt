package com.tewelde.articles.core.data

import com.tewelde.articles.core.data.store.ArticlesStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.first
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class RealArticlesRepository(
    val storeFactory: ArticlesStore.Factory
) : ArticlesRepository {

    val store by lazy { storeFactory.create() }

    override fun observeArticles(categoryId: String?): Flow<StoreReadResponse<ArticlesStore.Output>> {
        val operation = ArticlesStore.Operation.All(categoryId)
        val request = StoreReadRequest.cached(operation, refresh = false)
        return store.stream(request)
    }

    override fun observeArticle(articleId: String): Flow<StoreReadResponse<ArticlesStore.Output>> {
        val operation = ArticlesStore.Operation.Single(articleId)
        val request = StoreReadRequest.cached(operation, refresh = true)
        return store.stream(request)
    }

    override suspend fun sync() {
        val operation = ArticlesStore.Operation.All()
        val request = StoreReadRequest.fresh(operation)
        store.stream(request)
            .filterNot { it is StoreReadResponse.Loading }
            .first()
    }
}