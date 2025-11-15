package com.tewelde.articles

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.tewelde.articles.core.common.di.ComponentHolder
import com.tewelde.articles.di.DesktopAppComponent
import com.tewelde.articles.di.DesktopUiComponent
import com.tewelde.articles.di.create

fun main() {
    DesktopAppComponent::class.create().also {
        ComponentHolder.components += it
    }

    val uiComponent: DesktopUiComponent = ComponentHolder
        .component<DesktopUiComponent.Factory>()
        .create().also {
            ComponentHolder.components += it
        }

    return application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Help Articles",
        ) {
            uiComponent.appUi.Content(
                onRootPop = { /* no-op */ },
            )
        }
    }
}