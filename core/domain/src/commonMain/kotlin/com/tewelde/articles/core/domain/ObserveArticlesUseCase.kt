package com.tewelde.articles.core.domain

import com.tewelde.articles.core.common.ArticlesAppError
import com.tewelde.articles.core.common.LoadState
import com.tewelde.articles.core.data.ArticlesRepository
import com.tewelde.articles.core.data.store.ArticlesStore
import com.tewelde.articles.core.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.scan
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.StoreReadResponse
import org.mobilenativefoundation.store.store5.StoreReadResponseOrigin

/**
 * Use case to observe an article.
 */
@Inject
class ObserveArticlesUseCase(
    private val repo: ArticlesRepository
) {
    operator fun invoke(category: String? = null): Flow<LoadState<List<Article>>> =
        repo.observeArticles(category)
            .filterNot { it is StoreReadResponse.Loading || it is StoreReadResponse.NoNewData }
            .scan(Pair<StoreReadResponse<ArticlesStore.Output>?, Boolean>(null, false)) { acc, response ->
                val cachePresent = acc.second || (response is StoreReadResponse.Data<*> &&
                        response.dataOrNull()?.let { (it as? ArticlesStore.Output.Collection)?.articles?.isNotEmpty() } == true)
                Pair(response, cachePresent)
            }
            .mapNotNull { (response, cachePresent) ->
                when (response) {
                    is StoreReadResponse.Data<*> -> {
                        response.dataOrNull()?.let { output ->
                            val articles = (output as ArticlesStore.Output.Collection).articles
                            // this prevents emitting an empty list from the database cache while waiting for fresh network data
                            if (articles.isEmpty() && response.origin == StoreReadResponseOrigin.SourceOfTruth) {
                                return@mapNotNull null
                            }
                            LoadState.Loaded(articles)
                        }
                    }
                    is StoreReadResponse.Error -> {
                        // propagate the network error only if no cache is available
                        if (response.origin is StoreReadResponseOrigin.Fetcher && cachePresent) {
                            return@mapNotNull null
                        }

                        val articlesAppError = when (response) {
                            is StoreReadResponse.Error.Exception -> {
                                when (val throwable = response.error) {
                                    is ArticlesAppError -> throwable
                                    else -> ArticlesAppError.UnknownError(throwable)
                                }
                            }
                            is StoreReadResponse.Error.Message -> {
                                ArticlesAppError.UnknownError(Exception(response.message))
                            }
                            is StoreReadResponse.Error.Custom<*> -> {
                                when (val errorObj = response.error) {
                                    is ArticlesAppError -> errorObj
                                    is Throwable -> ArticlesAppError.UnknownError(errorObj)
                                    else -> ArticlesAppError.UnknownError(Exception(errorObj.toString()))
                                }
                            }
                        }
                        LoadState.Error(articlesAppError) as LoadState<List<Article>>
                    }
                    else -> null
                }
            }
}