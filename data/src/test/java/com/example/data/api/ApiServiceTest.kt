package com.example.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.BuildConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ApiServiceTest {
 var apiService: ApiService?=null
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val retrofit=Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        apiService= retrofit.create(ApiService::class.java)    }

    @After
    fun tearDown() {
        apiService=null
    }

    @Test
    fun getWeather() = runBlocking<Unit>{
        //Given
        val lat=27.55
        val lng=62.58
        val language="en"
        val apiKey=BuildConfig.ApiKey
        //   When
        apiService?.getWeather(lat,lng,language,apiKey)

        // Then
    //    MatcherAssert.assertThat(resultWeatherResponse,  IsNull.notNullValue())

    }
}