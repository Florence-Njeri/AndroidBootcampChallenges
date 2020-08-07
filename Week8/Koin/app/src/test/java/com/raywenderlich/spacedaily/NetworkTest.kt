package com.raywenderlich.spacedaily

import com.raywenderlich.spacedaily.di.networkModule
import com.raywenderlich.spacedaily.network.NASAAPIInterface
import com.squareup.moshi.Moshi
import junit.framework.Assert.assertNotNull
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import retrofit2.Retrofit

class NetworkTest : KoinTest {
    val nasaapiInterface: NASAAPIInterface by inject()
    val moshi: Moshi by inject()
    val retrofit: Retrofit by inject()
    val okHttpClient: OkHttpClient by inject()
    val baseUrl: String by lazy { get(named("BASE_URL")) as String }

    @Before
    fun setUp() {
        startKoin {
            modules(listOf(networkModule))
        }
    }

    @Test
    fun `Test Retrofit Create`() {
        assertNotNull(retrofit)
        assert(baseUrl == "https://api.nasa.gov/planetary/" )
    }

    @After
    fun tearDown() {
        stopKoin()
    }

}