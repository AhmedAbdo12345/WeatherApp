package com.example.data.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.database.weather.WeatherDBModel
import com.example.data.database.weather.WeatherDao
import com.example.data.database.weather.WeatherDatabase
import com.example.data.mapper.toDomainModel
import com.example.domain.entity.ModelApi.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.collection.IsEmptyCollection
import org.hamcrest.core.Is
import org.hamcrest.core.IsNull
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class WeatherDBRepoImplTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    val listWeather: List<Weather> = emptyList()
    val listDaily: List<Daily> = emptyList()
    val listHourly: List<Hourly> = emptyList()
    val listMinutely: List<Minutely> = emptyList()
    var current = Current(
        0, 0.0, 1, 2.0, 2,
        1, 1, 0, 4.0, 14.0, 5,
        listWeather, 1, 0.0, 0.0
    )

    val weatherDBModel = WeatherDBModel(
        1, "Suez", current, listDaily, listHourly,
        0.0, 0.0, listMinutely, "Egpt", 1
    )


    lateinit var db: WeatherDatabase
    lateinit var weatherDBRepoImpl: WeatherDBRepoImpl

    @Before
    fun initDB() {
        //   initialization Database
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries().build()

        weatherDBRepoImpl = WeatherDBRepoImpl(db)


    }

    @After
    fun closeDB() {
        //   close Database
        db.close()
    }

    @Test
    fun getWeather_insertWeatherResponse_countResponse() = runBlocking<Unit> {
        // Given
        weatherDBRepoImpl.insert(weatherDBModel.toDomainModel())

        // When
        var result = weatherDBRepoImpl.getAll()

        // Then
        MatcherAssert.assertThat(result.size, Is.`is`(1))

    }

    @Test
    fun insertWeatherResponse_insertWeatherResponseInRoom_CheckItemIsNotNull() = runBlocking {
        // Given
        var fakeModel = weatherDBModel.toDomainModel()
        //When
        weatherDBRepoImpl.insert(fakeModel)

        // Then
        var result = weatherDBRepoImpl.getAll()
        MatcherAssert.assertThat(result[0], IsNull.notNullValue())
    }

    @Test
    fun deleteWeatherResponse_insertWeatherResponseInRoom_CheckSizeIsZero() = runBlocking {
        // Given
        weatherDBRepoImpl.insert(weatherDBModel.toDomainModel())

        //  When
        weatherDBRepoImpl.delete(weatherDBModel.toDomainModel())

        // Then
        var result = weatherDBRepoImpl.getAll()
        assertThat(result, IsEmptyCollection.empty())
        assertThat(result.size, CoreMatchers.`is`(0))
    }
}