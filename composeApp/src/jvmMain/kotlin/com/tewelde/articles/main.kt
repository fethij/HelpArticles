package com.tewelde.articles

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.tewelde.articles.core.common.di.ComponentHolder
import com.tewelde.articles.di.DesktopAppComponent
import com.tewelde.articles.di.DesktopUiComponent
import com.tewelde.articles.di.create

fun main() = application {
    val appComponent = remember {
        DesktopAppComponent::class.create().also { component ->
            ComponentHolder.components += component
            component.appInitializers.initialize()
        }
    }
    val uiComponent: DesktopUiComponent = remember {
        ComponentHolder
            .component<DesktopUiComponent.Factory>()
            .create().also {
                ComponentHolder.components += it
            }
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Help Articles",
    ) {
        uiComponent.appUi.Content(
            onRootPop = { /* no-op */ },
        )
    }
}