package com.tewelde.articles.core.sync

import co.touchlab.kermit.Logger
import dev.mattramotar.meeseeks.runtime.AppContext
import dev.mattramotar.meeseeks.runtime.RuntimeContext
import dev.mattramotar.meeseeks.runtime.TaskResult
import dev.mattramotar.meeseeks.runtime.Worker
import me.tatarka.inject.annotations.Inject

@Inject
class SyncWorker(
    appContext: AppContext,
) : Worker<SyncPayload>(appContext) {
    override suspend fun run(
        payload: SyncPayload,
        context: RuntimeContext
    ): TaskResult {
        Logger.d { "#### Running SyncWorker with payload: $payload" }
        return TaskResult.Success
    }
}