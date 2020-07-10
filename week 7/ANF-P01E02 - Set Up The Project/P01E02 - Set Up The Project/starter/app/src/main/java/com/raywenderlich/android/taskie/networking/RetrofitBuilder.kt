package com.raywenderlich.android.taskie.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


//Add the logging interceptor to the api client so as to intercept the api requests and log stuff to Logcat
fun buildClient() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            //Log the body of the sent and received data
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

fun buildRetrofit(): Retrofit {
//Build the content type for your Kotlin parser
    val contentType = "application/json".toMediaType()
    return Retrofit.Builder()
            .client(buildClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.nonstrict.asConverterFactory(contentType))
            .build()
}

fun buildApiService() =
        buildRetrofit().create(RemoteApiService::class.java)
