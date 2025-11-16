package com.tewelde.articles.core.sync.di

import com.tewelde.articles.core.sync.SyncPayload
import com.tewelde.articles.core.sync.SyncWorker
import dev.mattramotar.meeseeks.runtime.ConfigurationScope
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

expect interface PlatformSyncComponent

@ContributesTo(AppScope::class)
interface NonWebSyncComponent : PlatformSyncComponent {

    @Provides
    @SingleIn(AppScope::class)
    fun provideMeeseeksConfiguration(): ConfigurationScope.() -> Unit = {
//        minBackoff(20.seconds)
        maxRetryCount(3)
        maxParallelTasks(5)
        allowExpedited()
        register<SyncPayload>(SyncPayload.stableId) { meeseeksAppContext ->
            SyncWorker(meeseeksAppContext)
        }
    }
}