package com.tewelde.articles

import com.tewelde.articles.core.common.di.ComponentHolder

object IosComponentHolder {
    fun addComponent(component: Any) {
        ComponentHolder.components += component
    }
}