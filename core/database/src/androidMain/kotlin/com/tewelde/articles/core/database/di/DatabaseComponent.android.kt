package com.tewelde.articles.core.database.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tewelde.articles.core.database.ArticleDatabase
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

actual interface DatabasePlatformComponent {
    @Provides
    @SingleIn(AppScope::class)
    fun provideDatabaseBuilder(
        application: Application
    ): RoomDatabase.Builder<ArticleDatabase> {
        val appContext = application.applicationContext
        val dbFile = appContext.getDatabasePath("articles.db")
        return Room.databaseBuilder<ArticleDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}