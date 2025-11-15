package com.tewelde.articles.core.common.di

import kotlinx.coroutines.CoroutineDispatcher

expect fun providePlatformIoDispatcher(): CoroutineDispatcher

enum class ArticlesDispatchers {
    IO, Default
}