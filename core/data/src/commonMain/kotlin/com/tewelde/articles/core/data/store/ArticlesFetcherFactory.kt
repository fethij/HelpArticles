package com.tewelde.articles.core.data.store

import com.tewelde.articles.core.network.ArticlesNetworkDataSource
import org.mobilenativefoundation.store.store5.Fetcher
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class ArticlesFetcherFactory(
    private val api: ArticlesNetworkDataSource
) {

    fun create(): Fetcher<ArticlesStore.Operation, ArticlesStore.Output> =
        Fetcher.ofResult { operation ->
            require(operation is ArticlesStore.Operation.All || operation is ArticlesStore.Operation.Single)
            fetcherResult {
                when (operation) {
                    is ArticlesStore.Operation.All -> {
                        val articles =
                            api.getArticles(operation.category).map { it.toDomainModel() }
                        ArticlesStore.Output.Collection(articles)
                    }

                    is ArticlesStore.Operation.Single -> {
                        val detail = api.getDetail(operation.articleId).toDomainModel()
                        ArticlesStore.Output.Single(detail)
                    }
                }
            }

        }
}