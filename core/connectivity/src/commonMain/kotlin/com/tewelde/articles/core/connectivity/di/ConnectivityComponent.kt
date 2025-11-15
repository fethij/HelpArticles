package com.tewelde.articles.core.connectivity.di

import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

expect interface PlatformConnectivityComponent

@ContributesTo(AppScope::class)
interface ConnectivityComponent : PlatformConnectivityComponent
