package com.tewelde.articles.core.sync.di

import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

expect interface PlatformSyncComponent

@ContributesTo(AppScope::class)
interface NonWebSyncComponent : PlatformSyncComponent