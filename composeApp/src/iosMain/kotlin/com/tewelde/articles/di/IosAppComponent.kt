package com.tewelde.articles.di

import dev.mattramotar.meeseeks.runtime.BGTaskManager
import me.tatarka.inject.annotations.Provides
import platform.UIKit.UIApplication
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.reflect.KClass

@SingleIn(AppScope::class)
@MergeComponent(AppScope::class)
abstract class IosAppComponent(
    @get:Provides val app: UIApplication
) : AppComponent {

    abstract val bGTaskManager: BGTaskManager

    companion object {
        fun create(
            app: UIApplication
        ) = IosAppComponent::class.createComponent(app)
    }
}

/**
 * The `actual fun` will be generated for each iOS specific target. See [MergeComponent] for more
 * details.
 */
@MergeComponent.CreateComponent
expect fun KClass<IosAppComponent>.createComponent(app: UIApplication): IosAppComponent