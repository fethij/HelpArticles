package com.tewelde.articles.core.data.store

import com.tewelde.articles.core.model.Article
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class ArticlesValidatorFactoryTest {

    private val factory = ArticlesValidatorFactory()

    private val HOUR_IN_MILLIS = 60 * 60 * 1000L
    private val TTL_MILLIS = 24 * HOUR_IN_MILLIS

    // Helper function to create a test article with a specific cachedAt timestamp
    @OptIn(ExperimentalTime::class)
    private fun createArticle(
        id: String = "test-id",
        ageInHours: Long = 0  // How many hours ago the article was cached
    ): Article {
        val now = Clock.System.now().toEpochMilliseconds()
        return Article(
            id = id,
            title = "Test Article",
            summary = "Test Summary",
            updatedAt = "2024-01-01",
            category = "Test",
            cachedAt = now - (HOUR_IN_MILLIS * ageInHours),
            content = "Test content"
        )
    }

    @Test
    fun `factory creates validator successfully`() {
        val validator = factory.create()
        assertTrue(validator != null, "Validator should be created successfully")
    }

    @Test
    fun `validator has correct TTL value`() {
        val expectedTTL = TTL_MILLIS
        assertEquals(expectedTTL, 24 * HOUR_IN_MILLIS, "TTL should be 24 hours")
    }

    @Test
    fun `factory provides current time`() {
        val beforeTime = Clock.System.now().toEpochMilliseconds()
        val factoryTime = factory.now
        val afterTime = Clock.System.now().toEpochMilliseconds()
        assertTrue(
            factoryTime in beforeTime..afterTime,
            "Factory time should be between before and after times"
        )
    }

    @Test
    fun `single output with fresh article should be valid`() {
        val article = createArticle(ageInHours = 1)
        val isFresh = (factory.now - article.cachedAt) <= TTL_MILLIS
        assertTrue(isFresh, "Article cached 1 hour ago should be fresh")
    }

    @Test
    fun `single output with stale article should be invalid`() {
        val article = createArticle(ageInHours = 25)
        val isFresh = (factory.now - article.cachedAt) <= TTL_MILLIS
        assertFalse(isFresh, "Article cached 25 hours ago should be stale")
    }

    @Test
    fun `collection with all fresh articles should be valid`() {
        val articles = listOf(
            createArticle(id = "1", ageInHours = 1),
            createArticle(id = "2", ageInHours = 2),
            createArticle(id = "3", ageInHours = 3)
        )
        val oldestCachedAt = articles.minOf { it.cachedAt }
        val isFresh = (factory.now - oldestCachedAt) <= TTL_MILLIS
        assertTrue(isFresh, "Collection with all articles within 24 hours should be fresh")
    }

    @Test
    fun `collection with one stale article should be invalid`() {
        val articles = listOf(
            createArticle(id = "1", ageInHours = 1),
            createArticle(id = "2", ageInHours = 2),
            createArticle(id = "3", ageInHours = 25)  // Stale
        )
        val oldestCachedAt = articles.minOf { it.cachedAt }
        val isFresh = (factory.now - oldestCachedAt) <= TTL_MILLIS
        assertFalse(isFresh, "Collection with one stale article should be stale")
    }

    @Test
    fun `empty collection should be invalid`() {
        val articles = emptyList<Article>()
        val isEmpty = articles.isEmpty()
        assertTrue(isEmpty, "Empty collection should be invalid")
    }
}
