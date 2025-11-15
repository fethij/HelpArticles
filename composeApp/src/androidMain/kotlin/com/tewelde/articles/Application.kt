package com.tewelde.articles

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.tewelde.articles.core.common.di.ComponentHolder
import com.tewelde.articles.di.AndroidAppComponent
import com.tewelde.articles.di.create
import dev.mattramotar.meeseeks.runtime.MeeseeksWorkerFactory
import kotlinx.coroutines.DelicateCoroutinesApi

class Application : Application(), Configuration.Provider {

    val androidComponent by lazy { AndroidAppComponent::class.create(this) }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()
        ComponentHolder.components += androidComponent
        androidComponent.appInitializers.initialize()
    }

    private val lazyWorkerFactory by lazy { LazyMeeseeksWorkerFactory() }
    override val workManagerConfiguration: Configuration by lazy {
//        val delegating by lazy {
//            DelegatingWorkerFactory().apply {
//                addFactory(
//                    MeeseeksWorkerFactory(
//                        androidComponent.bGTaskManager
//                    )
//                )
//            }
//        }
        Configuration.Builder()
            .setWorkerFactory(lazyWorkerFactory)
            .build()
    }

    /**
     * A lazy wrapper around MeeseeksWorkerFactory that defers accessing bGTaskManager
     * until a worker actually needs to be created. This prevents circular dependency issues
     * where Meeseeks.initialize() calls WorkManager.getInstance(), which would trigger
     * Configuration.Provider, which would access bGTaskManager, which calls Meeseeks.initialize() again.
     */
    private inner class LazyMeeseeksWorkerFactory : WorkerFactory() {
        private val meeseeksFactory by lazy {
            println("#### LazyMeeseeksWorkerFactory: Initializing MeeseeksWorkerFactory")
            MeeseeksWorkerFactory(androidComponent.bGTaskManager)
        }

        override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters
        ): ListenableWorker? {
            println("#### LazyMeeseeksWorkerFactory: Creating worker: $workerClassName")
            return meeseeksFactory.createWorker(appContext, workerClassName, workerParameters)
        }
    }
}