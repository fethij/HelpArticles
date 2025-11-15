package com.tewelde.articles.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {
    @Query("SELECT * FROM articles WHERE :category IS NULL OR category = :category")
    fun getAllArticles(category: String?): Flow<List<RealArticleEntity>>

    @Query("SELECT * FROM articles WHERE id = :articleId LIMIT 1")
    fun getArticleById(articleId: String): Flow<RealArticleEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: RealArticleEntity)

    //    @Delete
    @Query("DELETE FROM articles WHERE id = :articleId")
    suspend fun deleteArticle(articleId: String)
}