package com.tewelde.articles.core.sync

import com.tewelde.articles.core.common.initializers.AppInitializer
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

@Inject
@ContributesBinding(AppScope::class, multibinding = true)
class SyncInitializer(
    val syncManager: SyncManager,
) : AppInitializer {
    override fun initialize() {
        syncManager.sync()
    }
}