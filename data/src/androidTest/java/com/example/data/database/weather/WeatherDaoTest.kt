package com.example.data.database.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.data.database.fav.FavDao
import com.example.data.database.fav.FavDatabase
import com.example.data.database.fav.FavouriteModel
import com.example.domain.entity.ModelApi.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
@SmallTest
class WeatherDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var db: WeatherDatabase
    lateinit var dao: WeatherDao

    @Before
    fun initDB() {
        //   initialization Database
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.getWeatherDao()
    }

    @After
    fun closeDB() {
        //   close Database
        db.close()
    }

    @Test
    fun getWeather_insertWeatherResponse_countResponse() = runBlocking {
        // Given
        val listWeather: List<Weather> = emptyList()
        val listDaily: List<Daily> = emptyList()
        val listHourly: List<Hourly> = emptyList()
        val listMinutely: List<Minutely> = emptyList()
        var current = Current(0, 0.0, 1, 2.0, 2, 1, 1, 0, 4.0, 14.0, 5,
            listWeather, 1, 0.0, 0.0
        )
        val data = WeatherDBModel(1, "Suez", current, listDaily, listHourly,
            0.0, 0.0, listMinutely, "Egpt", 1)

        dao.insert(data)

        // When
        var result = dao.getAll()

        // Then
        MatcherAssert.assertThat(result.size, Is.`is`(1))
    }

    @Test
    fun insertWeather_insertWeatherResponse_returnItem() = runBlocking {
        // Given
        // Given
        val listWeather: List<Weather> = emptyList()
        val listDaily: List<Daily> = emptyList()
        val listHourly: List<Hourly> = emptyList()
        val listMinutely: List<Minutely> = emptyList()
        var current = Current(0, 0.0, 1, 2.0, 2, 1, 1, 0, 4.0, 14.0, 5,
            listWeather, 1, 0.0, 0.0
        )
        val data = WeatherDBModel(1, "Suez", current, listDaily, listHourly,
            0.0, 0.0, listMinutely, "Egpt", 1)

        // When
        dao.insert(data)
        // Then
        var result = dao.getAll()
        MatcherAssert.assertThat(result[0], IsNull.notNullValue())
    }

    @Test
    fun deleteWeather_deleteWeatherResponse_checkIsNull() = runBlocking {
        // Given
        val listWeather: List<Weather> = emptyList()
        val listDaily: List<Daily> = emptyList()
        val listHourly: List<Hourly> = emptyList()
        val listMinutely: List<Minutely> = emptyList()
        var current = Current(0, 0.0, 1, 2.0, 2, 1, 1, 0, 4.0, 14.0, 5,
            listWeather, 1, 0.0, 0.0
        )
        val data = WeatherDBModel(1, "Suez", current, listDaily, listHourly,
            0.0, 0.0, listMinutely, "Egpt", 1)
        dao.insert(data)

        // When
        dao.delete(data)
        // Then
        var result = dao.getAll()
        assertThat(result, IsEmptyCollection.empty())
        assertThat(result.size, CoreMatchers.`is`(0))
    }
}