package com.tewelde.articles.core.common.di

import kotlinx.coroutines.Dispatchers

actual fun providePlatformIoDispatcher() = Dispatchers.IO