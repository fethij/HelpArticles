package com.tewelde.articles.core.common.di.qualifier

import com.tewelde.articles.core.common.di.ArticlesDispatchers
import me.tatarka.inject.annotations.Qualifier

@Qualifier
@Target(
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.TYPE
)
annotation class Named(val value: ArticlesDispatchers)
