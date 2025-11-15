package com.tewelde.articles.core.database.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.tewelde.articles.core.common.di.ArticlesDispatchers
import com.tewelde.articles.core.common.di.qualifier.Named
import com.tewelde.articles.core.database.ArticleDatabase
import kotlinx.coroutines.CoroutineDispatcher
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

expect interface DatabasePlatformComponent

@ContributesTo(AppScope::class)
interface DatabaseComponent : DatabasePlatformComponent {

    @Provides
    @SingleIn(AppScope::class)
    fun provideDatabase(
        builder: RoomDatabase.Builder<ArticleDatabase>,
        @Named(ArticlesDispatchers.IO)
        ioDispatcher: CoroutineDispatcher
    ): ArticleDatabase = builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(ioDispatcher)
        .build()

    @Provides
    @SingleIn(AppScope::class)
    fun provideArticlesDao(
        database: ArticleDatabase
    ) = database.articleDao()
}


