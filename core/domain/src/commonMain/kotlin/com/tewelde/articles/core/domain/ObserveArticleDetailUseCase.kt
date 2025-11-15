package com.tewelde.articles.core.domain

import com.tewelde.articles.core.common.LoadState
import com.tewelde.articles.core.data.ArticlesRepository
import com.tewelde.articles.core.data.store.ArticlesStore
import com.tewelde.articles.core.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.mapNotNull
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.StoreReadResponse

/**
 * Use case to observe an article.
 */
@Inject
class ObserveArticleDetailUseCase(
    private val repo: ArticlesRepository
) {
    operator fun invoke(articleId: String): Flow<LoadState<Article>> =
        repo.observeArticle(articleId)
            .filterNot { it is StoreReadResponse.Loading || it is StoreReadResponse.NoNewData }
            .mapNotNull { response ->
                when (response) {
                    is StoreReadResponse.Data<*> -> {
                        response.dataOrNull()?.let { output ->
                            val article = (output as ArticlesStore.Output.Single).article
                            LoadState.Loaded(article)
                        }
                    }
                    //TODO  ideally show partial data with snackbar indicating error
//                    is StoreReadResponse.Error

                    else -> null
                }
            }
}