package com.tewelde.articles.core.common.initializers

interface Initializer {
    val priority: Int get() = DEFAULT_PRIORITY

    companion object Companion {
        const val LOWEST_PRIORITY = Int.MIN_VALUE
        const val DEFAULT_PRIORITY = 0
        const val HIGHEST_PRIORITY = Int.MAX_VALUE
    }
}
