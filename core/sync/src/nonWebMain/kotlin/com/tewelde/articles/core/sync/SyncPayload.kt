package com.tewelde.articles.core.sync

import dev.mattramotar.meeseeks.runtime.TaskPayload
import kotlinx.serialization.Serializable

@Serializable
data object SyncPayload : TaskPayload {

    const val stableId: String = V_1_0_0
}

private const val V_1_0_0 = "com.tewelde.articles.core.sync.SyncPayload:1.0.0"