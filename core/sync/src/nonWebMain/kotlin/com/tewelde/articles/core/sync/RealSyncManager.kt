package com.tewelde.articles.core.sync

import dev.mattramotar.meeseeks.runtime.BGTaskManager
import dev.mattramotar.meeseeks.runtime.oneTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.time.Duration.Companion.minutes

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class RealSyncManager(
    private val appScope: CoroutineScope,
    private val bgTaskManager: BGTaskManager
) : SyncManager {
    override fun sync() {
        appScope.launch {
            val taskHandle = bgTaskManager.oneTime(SyncPayload) {
                requireNetwork(true)
                highPriority()
                retryWithExponentialBackoff(
                    initialDelay = 1.minutes,
                    maxAttempts = 3
                )
            }
            taskHandle
                .observe()
                .collect { status ->
                    // Pending -> Running -> Finished
                    println("#### Sync task status: $status")
                }
        }

    }

}