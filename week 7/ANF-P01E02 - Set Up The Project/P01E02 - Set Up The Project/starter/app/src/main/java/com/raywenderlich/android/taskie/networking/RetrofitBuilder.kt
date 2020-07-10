package com.raywenderlich.android.taskie.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

//Build the content type for your Kotlin parser


fun buildClient() = OkHttpClient.Builder()
        .build()

fun buildRetrofit() : Retrofit{
    val contentType = "application/json".toMediaType()
   return Retrofit.Builder()
            .client(buildClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.nonstrict.asConverterFactory(contentType))
            .build()
}


fun buildApiService() =
        buildRetrofit().create(RemoteApiService::class.java)
