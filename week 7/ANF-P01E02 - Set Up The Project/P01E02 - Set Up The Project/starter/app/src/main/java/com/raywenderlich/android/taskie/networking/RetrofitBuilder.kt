package com.raywenderlich.android.taskie.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.raywenderlich.android.taskie.App
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


//Add the logging interceptor to the api client so as to intercept the api requests and log stuff to Logcat

private const val AUTHORIZATION_HEADER = "Authorization"
fun buildClient() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            //Log the body of the sent and received data
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(buildAuthorizationInterceptor())
        .build()

fun buildAuthorizationInterceptor() = object : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //Chain is the process the api request goes through before returning a response
        val originalRequest = chain.request()
        /**Check if there is an app token saved in the shared prefs
         * If there isn't proceed with your request as is
         */
        if (App.getToken().isBlank()) return chain.proceed(originalRequest)
        /**
         * Else , if there is a token create a new request from the original one and  append the token to the header of the request
         */
        val new = originalRequest.newBuilder()
                .addHeader(AUTHORIZATION_HEADER, App.getToken()).build()

        return chain.proceed(new)
    }

}

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
