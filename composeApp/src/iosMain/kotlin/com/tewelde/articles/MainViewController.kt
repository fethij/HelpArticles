package com.tewelde.articles

import androidx.compose.ui.window.ComposeUIViewController
import com.tewelde.articles.core.common.di.ComponentHolder
import com.tewelde.articles.di.IosAppComponent
import com.tewelde.articles.di.IosUiComponent

fun MainViewController() = ComposeUIViewController(
    configure = {
        IosAppComponent.create().also { ComponentHolder.components += it }

        ComponentHolder
            .component<IosUiComponent.Factory>()
            .create()
            .also {
                ComponentHolder.components += it
            }
    }
) {
    val uiComponent = ComponentHolder.component<IosUiComponent>()
    uiComponent.appUi.Content(
        onRootPop = { /* no-op */ }
    )
}