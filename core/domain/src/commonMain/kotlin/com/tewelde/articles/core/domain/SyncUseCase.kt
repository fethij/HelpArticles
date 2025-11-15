package com.tewelde.articles.core.domain

import com.tewelde.articles.core.data.ArticlesRepository
import me.tatarka.inject.annotations.Inject

/**
 * Use case to sync articles.
 */
@Inject
class SyncUseCase(
    private val repo: ArticlesRepository
) {
    suspend operator fun invoke(): Unit = repo.sync()
}