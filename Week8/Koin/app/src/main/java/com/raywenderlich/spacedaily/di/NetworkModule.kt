package com.raywenderlich.spacedaily.di

import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModue = module {
    single(named("BASE_URL")) { }
}