package com.tewelde.articles

import androidx.compose.ui.window.ComposeUIViewController
import com.tewelde.articles.core.common.di.ComponentHolder
import com.tewelde.articles.di.IosUiComponent
import me.tatarka.inject.annotations.Inject
import platform.UIKit.UIViewController

typealias ArticlesUiViewController = () -> UIViewController

@Inject
fun ArticlesUiViewController(): UIViewController =
    ComposeUIViewController {
        val uiComponent = ComponentHolder.component<IosUiComponent>()
        uiComponent.appUi.Content(
            onRootPop = { /* no-op */ }
        )
    }