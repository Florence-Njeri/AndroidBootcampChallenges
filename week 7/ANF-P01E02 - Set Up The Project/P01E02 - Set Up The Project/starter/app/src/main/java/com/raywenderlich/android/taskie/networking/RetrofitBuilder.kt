package com.raywenderlich.android.taskie.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit

fun buildClient() = OkHttpClient.Builder()
        .build()

fun buildRetrofit() = Retrofit.Builder()
        .client(buildClient())
        .baseUrl(BASE_URL)
        .build()


fun buildApiService() =
        buildRetrofit().create(RemoteApiService::class.java)
