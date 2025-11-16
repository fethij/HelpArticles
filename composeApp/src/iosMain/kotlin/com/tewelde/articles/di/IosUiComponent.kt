package com.tewelde.articles.di

import com.tewelde.articles.ArticlesUiViewController
import com.tewelde.articles.core.common.di.UiScope
import me.tatarka.inject.annotations.Provides
import platform.UIKit.UIViewController
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@SingleIn(UiScope::class)
@ContributesSubcomponent(UiScope::class)
interface IosUiComponent : SharedUiComponent {
    val uiViewControllerFactory: () -> UIViewController

    @Provides
    @SingleIn(UiScope::class)
    fun uiViewController(impl: ArticlesUiViewController): UIViewController = impl()

    @ContributesSubcomponent.Factory(AppScope::class)
    interface Factory {
        fun create(): IosUiComponent
    }
}
