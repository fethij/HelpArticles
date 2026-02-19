package com.tewelde.articles.core.sync

import co.touchlab.kermit.Logger
import com.tewelde.articles.core.domain.SyncUseCase
import dev.mattramotar.meeseeks.runtime.AppContext
import dev.mattramotar.meeseeks.runtime.RuntimeContext
import dev.mattramotar.meeseeks.runtime.TaskResult
import dev.mattramotar.meeseeks.runtime.Worker
import me.tatarka.inject.annotations.Inject

@Inject
class SyncWorker(
    appContext: AppContext,
    private val syncUseCase: SyncUseCase,
) : Worker<SyncPayload>(appContext) {
    override suspend fun run(
        payload: SyncPayload,
        context: RuntimeContext
    ): TaskResult {
        Logger.d { "#### Running SyncWorker with payload: $payload" }
        syncUseCase()
        Logger.d { "#### SyncWorker completed" }
        return TaskResult.Success
    }
}