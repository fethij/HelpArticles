package com.tewelde.articles.core.common.di

import com.tewelde.articles.core.common.di.qualifier.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface CommonComponent {
    @Provides
    @SingleIn(AppScope::class)
    fun provideIoDispatcher(): @Named(ArticlesDispatchers.IO) CoroutineDispatcher =
        providePlatformIoDispatcher()

    @Provides
    @SingleIn(AppScope::class)
    fun provideDefaultDispatcher(): @Named(ArticlesDispatchers.Default) CoroutineDispatcher =
        Dispatchers.Default

    @Provides
    @SingleIn(AppScope::class)
    fun provideApplicationScope(
        @Named(ArticlesDispatchers.Default) dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}