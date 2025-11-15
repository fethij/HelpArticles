package com.tewelde.articles.core.sync.di

import android.app.Application
import com.tewelde.articles.core.sync.SyncPayload
import com.tewelde.articles.core.sync.SyncWorker
import dev.mattramotar.meeseeks.runtime.BGTaskManager
import dev.mattramotar.meeseeks.runtime.Meeseeks
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

actual interface PlatformSyncComponent {
    @Provides
    @SingleIn(AppScope::class)
    fun provideBGTaskManager(
        application: Application
    ): BGTaskManager {
        val androidContext = application.applicationContext
        val manager = Meeseeks.initialize(androidContext) {
//            minBackoff(20.seconds)
//            maxRetryCount(3)
//            maxParallelTasks(5)
            allowExpedited()
            register<SyncPayload>(SyncPayload.stableId) { meeseeksAppContext ->
                SyncWorker(meeseeksAppContext)
            }
        }
        return manager
    }
}