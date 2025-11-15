package com.tewelde.articles

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.tewelde.articles.core.common.di.ComponentHolder
import com.tewelde.articles.di.WebAppComponent
import com.tewelde.articles.di.WebUiComponent
import com.tewelde.articles.di.create
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    WebAppComponent::class.create().also { ComponentHolder.components += it }

    val uiComponent = ComponentHolder
        .component<WebUiComponent.Factory>()
        .create().also {
            ComponentHolder.components += it
        }

    ComposeViewport(document.body!!) {
        /**
         * Disable disk cache for wasm-js target to avoid UnsupportedOperationException.
         * @see [FileSystem.SYSTEM_TEMPORARY_DIRECTORY]
         */
        uiComponent.appUi.Content(
            onRootPop = { /* no op */ }
        )
    }
}
