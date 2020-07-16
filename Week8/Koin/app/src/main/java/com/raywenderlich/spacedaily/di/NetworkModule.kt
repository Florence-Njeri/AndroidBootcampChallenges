package com.raywenderlich.spacedaily.di

import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModue = module {
    single(named("BASE_URL")) { "https://api.nasa.gov/planetary/" }
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        interceptor
    }
}