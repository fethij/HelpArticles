package com.tewelde.articles

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.tewelde.articles.SyncWorker.Companion.SyncWorkerName
import com.tewelde.articles.SyncWorker.Companion.startUpPeriodicSyncWork
import com.tewelde.articles.core.common.di.ComponentHolder
import com.tewelde.articles.di.AndroidAppComponent
import com.tewelde.articles.di.create
import kotlinx.coroutines.DelicateCoroutinesApi

class Application : Application() {

    val androidComponent by lazy { AndroidAppComponent::class.create(this) }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()
        ComponentHolder.components += androidComponent
        enqueuePeriodicSyncWork()
    }

    fun enqueuePeriodicSyncWork() {
        WorkManager.getInstance(this).apply {
            enqueueUniquePeriodicWork(
                SyncWorkerName,
                ExistingPeriodicWorkPolicy.KEEP,
                startUpPeriodicSyncWork()
            )
        }
    }
}