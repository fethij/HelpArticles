package com.tewelde.articles

import com.tewelde.articles.di.IosAppComponent
import com.tewelde.articles.di.IosUiComponent
import platform.UIKit.UIApplication

object IosComponentCreator {

    fun createAppComponent(app: UIApplication): IosAppComponent = IosAppComponent.create(app)

    fun createUiComponent(appComponent: IosAppComponent): IosUiComponent =
        (appComponent as IosUiComponent.Factory).create()
}