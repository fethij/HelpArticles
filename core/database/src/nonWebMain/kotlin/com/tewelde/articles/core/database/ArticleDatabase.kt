package com.tewelde.articles.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(entities = [RealArticleEntity::class], version = 1)
@ConstructedBy(ArticleDatabaseConstructor::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticlesDao
}

@Suppress("KotlinNoActualForExpect")
expect object ArticleDatabaseConstructor : RoomDatabaseConstructor<ArticleDatabase> {
    override fun initialize(): ArticleDatabase
}