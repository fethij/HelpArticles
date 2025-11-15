package com.tewelde.articles.core.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.tewelde.articles.core.database.ArticleDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import me.tatarka.inject.annotations.Provides
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

actual interface DatabasePlatformComponent {
    @Provides
    @SingleIn(AppScope::class)
    fun provideDatabaseBuilder(): RoomDatabase.Builder<ArticleDatabase> {
        val dbFilePath = documentDirectory() + "/articles.db"
        return Room.databaseBuilder<ArticleDatabase>(
            name = dbFilePath,
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path)
    }
}