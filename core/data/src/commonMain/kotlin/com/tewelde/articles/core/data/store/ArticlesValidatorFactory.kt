package com.tewelde.articles.core.data.store

import com.tewelde.articles.core.data.store.ArticlesStore.Output
import org.mobilenativefoundation.store.store5.Validator
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class ArticlesValidatorFactory {

    private val ttlMillis: Long = 24.hours.inWholeMilliseconds
    val now: Long
        get() = Clock.System.now().toEpochMilliseconds()

    fun create(): Validator<Output> = Validator.by { output ->
        when (output) {
            is Output.Single -> {
                (now - output.article.cachedAt) <= ttlMillis
            }

            is Output.Collection -> {
                if (output.articles.isEmpty()) return@by false
                // Check the oldest (minimum) cachedAt - if it's valid, all are valid
                val oldestCachedAt = output.articles.minOf { it.cachedAt }
                (now - oldestCachedAt) <= ttlMillis
            }
        }
    }
}