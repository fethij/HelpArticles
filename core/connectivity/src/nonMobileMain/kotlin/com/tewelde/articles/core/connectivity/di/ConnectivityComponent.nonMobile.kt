package com.tewelde.articles.core.connectivity.di

import dev.jordond.connectivity.Connectivity
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

actual interface PlatformConnectivityComponent {
    @Provides
    @SingleIn(AppScope::class)
    fun provideConnectivity(appScope: CoroutineScope): Connectivity =
        Connectivity(
            scope = appScope,
        )
}
