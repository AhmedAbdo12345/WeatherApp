package com.example.weather.viewmodel

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.domain.entity.ModelApi.*
import com.example.domain.repo.WeatherRepo
import com.example.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsNull
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class WeatherViewModelTest {

   lateinit var weatherViewModel:WeatherViewModel

    @Before
    fun setUp() {
        val fakeWeatherRepo=FakeWeatherRepo()
        val getWeatherUseCase=GetWeatherUseCase(fakeWeatherRepo)
        weatherViewModel=WeatherViewModel(getWeatherUseCase)
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
        var resultWeatherResponse= weatherViewModel.getWeather(lat,lng,language,apiKey)

        // Then
        MatcherAssert.assertThat(resultWeatherResponse,  IsNull.notNullValue())
    }
}
class FakeWeatherRepo : WeatherRepo{
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

    override suspend fun getWeatherFromApi(
        lat: Double,
        lon: Double,
        lang: String,
        apiKey: String
    ): WeatherResponse {
       return WeatherResponse
    }

}