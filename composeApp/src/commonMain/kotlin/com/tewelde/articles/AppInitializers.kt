package com.tewelde.articles

import co.touchlab.kermit.Logger
import com.tewelde.articles.core.common.initializers.AppInitializer
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
class AppInitializers(
    initializers: Set<AppInitializer>,
) : AppInitializer {
    private val sortedInitializers = initializers.sortedByDescending { it.priority }

    override fun initialize() {
        for (initializer in sortedInitializers) {
            initializer.initialize().also {
                Logger.d { "${initializer::class.simpleName} initialized" }
            }
        }
    }
}
