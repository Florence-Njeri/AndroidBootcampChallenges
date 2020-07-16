package com.raywenderlich.spacedaily.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.raywenderlich.spacedaily.BuildConfig
import com.raywenderlich.spacedaily.network.NASAAPIInterface
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val networkModule = module {
    single(named("BASE_URL")) { "https://api.nasa.gov/planetary/" }
    //Create the logging interceptor
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        interceptor
    }

    //Add the Okhttp client
    single {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            client.addInterceptor(get<HttpLoggingInterceptor>())
        }
        client.build()
    }
    //Create Retrofit
    single {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl(get<String>(named("BASE_URL")))
            .addConverterFactory(Json.asConverterFactory(contentType))
            .client(get())
            .build()
    }

    //Now create Api Service endpoint
    single {
        get<Retrofit>().create(NASAAPIInterface::class.java)
    }
}