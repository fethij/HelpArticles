package com.tewelde.articles.core.sync.di

import com.tewelde.articles.core.sync.IosAppContext
import dev.mattramotar.meeseeks.runtime.BGTaskManager
import dev.mattramotar.meeseeks.runtime.ConfigurationScope
import dev.mattramotar.meeseeks.runtime.Meeseeks
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

actual interface PlatformSyncComponent {

    @Provides
    @SingleIn(AppScope::class)
    fun provideBGTaskManager(
        context: IosAppContext,
        configure: ConfigurationScope.() -> Unit
    ): BGTaskManager = Meeseeks.initialize(context) { configure() }
}