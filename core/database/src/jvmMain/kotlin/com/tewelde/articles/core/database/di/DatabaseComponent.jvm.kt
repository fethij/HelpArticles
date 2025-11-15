package com.tewelde.articles.core.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.tewelde.articles.core.database.ArticleDatabase
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import java.io.File

actual interface DatabasePlatformComponent {
    @Provides
    @SingleIn(AppScope::class)
    fun provideDatabaseBuilder(): RoomDatabase.Builder<ArticleDatabase> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), "articles.db")
        return Room.databaseBuilder<ArticleDatabase>(
            name = dbFile.absolutePath,
        )
    }
}