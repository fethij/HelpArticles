package com.tewelde.articles.core.data.store

import com.tewelde.articles.core.data.store.ArticlesStore.Output
import com.tewelde.articles.core.database.model.ArticleEntity
import org.mobilenativefoundation.store.store5.Converter
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class ArticlesConverterFactory {

    fun create(): Converter<Output, List<ArticleEntity>, Output> =
        Converter.Builder<Output, List<ArticleEntity>, Output>()
            .fromNetworkToLocal { output ->
                when (output) {
                    is Output.Single -> listOf(output.article.toEntity())
                    is Output.Collection -> output.articles.map { it.toEntity() }
                }
            }
            .fromOutputToLocal { output ->
                when (output) {
                    is Output.Single -> listOf(output.article.toEntity())
                    is Output.Collection -> output.articles.map { it.toEntity() }
                }
            }
            .build()
}