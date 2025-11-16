package com.tewelde.articles.core.sync
import dev.mattramotar.meeseeks.runtime.AppContext
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

/**
 * jvm implementation of AppContext.
 * On jvm, AppContext is an abstract class with no required implementation.
 */
@Inject
@SingleIn(AppScope::class)
class JvmAppContext : AppContext()