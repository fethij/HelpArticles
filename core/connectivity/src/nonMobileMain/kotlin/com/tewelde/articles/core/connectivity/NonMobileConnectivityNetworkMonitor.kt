package com.tewelde.articles.core.connectivity

import co.touchlab.kermit.Logger
import dev.jordond.connectivity.Connectivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class NonMobileConnectivityNetworkMonitor(
    appScope: CoroutineScope,
    private val connectivity: Connectivity,
) : NetworkMonitor {

    override val isConnected: StateFlow<Boolean> =
        connectivity
            .statusUpdates
            .onStart {
                Logger.d { "Network status updates started" }
                // Get the current connectivity status immediately
                emit(connectivity.status())
            }.map { it.isConnected }
            .stateIn(
                scope = appScope,
                started = SharingStarted.Companion.Eagerly,
                initialValue = true
            )
}