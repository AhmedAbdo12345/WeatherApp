package com.example.data.repo

import com.example.data.api.ApiService
import com.example.domain.entity.ModelApi.*
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsNull
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class WeatherRepoImplTest {
lateinit var apiService: ApiService

 var fakeApiService= FakeApiService()

lateinit var weatherRepoImpl: WeatherRepoImpl
    @Before
    fun setUp() {
weatherRepoImpl= WeatherRepoImpl(fakeApiService)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getWeatherFromApi_passLocationInfo_CheckResponseIsNotNull() = runBlocking{
        //Given
        val lat=27.55
        val lng=62.58
        val language="en"
        val apiKey="cvmhjhvgvhgvgcgcghtfghv"
        //   When
       var resultWeatherResponse= weatherRepoImpl.getWeatherFromApi(lat,lng,language,apiKey)

        // Then
        MatcherAssert.assertThat(resultWeatherResponse,  IsNull.notNullValue())
    }
}
class FakeApiService:ApiService{

    val listWeather: List<Weather> = emptyList()
    val listDaily: List<Daily> = emptyList()
    val listHourly: List<Hourly> = emptyList()
    val listMinutely: List<Minutely> = emptyList()
    var current = Current(
        0, 0.0, 1, 2.0, 2,
        1, 1, 0, 4.0, 14.0, 5,
        listWeather, 1, 0.0, 0.0)

    val WeatherResponse = WeatherResponse(current, listDaily, listHourly,
        0.0, 0.0, listMinutely, "Egpt", 1)


    override suspend fun getWeather(
        lat: Double,
        lon: Double,
        lang: String,
        apiKey: String
    ): WeatherResponse {
        return WeatherResponse
    }

}