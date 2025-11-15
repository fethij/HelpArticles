package com.tewelde.articles

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

/**
 * Worker to sync data in the background
 */
class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    private val sync by lazy {
        (applicationContext as Application).androidComponent.syncUseCase
    }

    override suspend fun doWork(): Result {
        return runCatching { sync() }
            .fold(
                onSuccess = {
                    Result.success()
                },
                onFailure = { _ ->
                    Result.retry()
                }
            )
    }

    companion object {
        const val SyncWorkerName = "sync_worker"

        /**
         * Periodic work to upload logs
         */
        fun startUpPeriodicSyncWork() =
            PeriodicWorkRequestBuilder<SyncWorker>(24, TimeUnit.HOURS)
                .setConstraints(SyncWorkerConstraints)
                .build()

        val SyncWorkerConstraints = Constraints.Builder()
            .setRequiredNetworkType(androidx.work.NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()
    }
}