@file:OptIn(ExperimentalTime::class)

package com.tewelde.articles.core.data.store

import com.tewelde.articles.core.database.ArticlesLocalDataSource
import com.tewelde.articles.core.model.Article
import com.tewelde.articles.core.network.ArticlesNetworkDataSource
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import kotlin.time.ExperimentalTime

typealias ArticleId = String
typealias Category = String

object ArticlesStore {

    @Inject
    class Factory(
        db: ArticlesLocalDataSource,
        api: ArticlesNetworkDataSource
    ) {
        private val fetcherFactory = ArticlesFetcherFactory(api)
        private val sourceOfTruthFactory = ArticlesSourceOfTruthFactory(db = db)
        private val articlesValidatorFactory = ArticlesValidatorFactory()
        private val articlesCFactory = ArticlesConverterFactory()


        fun create(): Store<Operation, Output> {
            return StoreBuilder.from(
                fetcher = fetcherFactory.create(),
                sourceOfTruth = sourceOfTruthFactory.create(),
                converter = articlesCFactory.create(),
            ).validator(
                validator = articlesValidatorFactory.create()
            ).build()
        }
    }

    sealed interface Operation {
        data class All(
            val category: Category? = null
        ) : Operation

        data class Single(
            val articleId: ArticleId
        ) : Operation
    }

    sealed interface Output {
        fun isEmpty(): Boolean

        data class Single(val article: Article) : Output {
            override fun isEmpty(): Boolean = false
        }

        data class Collection(val articles: List<Article>) : Output {
            override fun isEmpty(): Boolean = articles.isEmpty()
        }
    }
}
