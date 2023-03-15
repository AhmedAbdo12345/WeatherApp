package com.example.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.mapper.toDataModel
import com.example.domain.entity.ModelApi.*
import com.example.domain.entity.ModelDatabase.WeatherDB
import com.example.domain.repo.WeatherDBRepo
import com.example.domain.usecase.WeatherDBUseCase.DeleteWeatheDBUseCase
import com.example.domain.usecase.WeatherDBUseCase.GetWeatherDBUseCase
import com.example.domain.usecase.WeatherDBUseCase.InsertWeatherDBUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.collection.IsEmptyCollection
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class WeatherDatabaseViewModelTest{
    //--------------------------------------------------------------------------
    val listWeather: List<Weather> = emptyList()
    val listDaily: List<Daily> = emptyList()
    val listHourly: List<Hourly> = emptyList()
    val listMinutely: List<Minutely> = emptyList()
    var current = Current(
        0, 0.0, 1, 2.0, 2,
        1, 1, 0, 4.0, 14.0, 5,
        listWeather, 1, 0.0, 0.0
    )

    val weatherDB = WeatherDB(
        1, "Suez", current, listDaily, listHourly,
        0.0, 0.0, listMinutely, "Egpt", 1
    )
    val weatherDB2 = WeatherDB(
        2, "Cairo", current, listDaily, listHourly,
        0.0, 0.0, listMinutely, "Egpt", 1
    )
    val fakeList: MutableList<WeatherDB> = mutableListOf (weatherDB,weatherDB2)
//--------------------------------------------------------------------------

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    lateinit var weatherDatabaseViewModel: WeatherDatabaseViewModel

    var fakeWeatherDBRepo=FakeWeatherDBRepo()

    @Before
    fun init()  {
        //   initialization Database


        var getWeatherUseCase=GetWeatherDBUseCase(fakeWeatherDBRepo)
        var deleteWeatheDBUseCase=DeleteWeatheDBUseCase(fakeWeatherDBRepo)
        var insertWeatherDBUseCase=InsertWeatherDBUseCase(fakeWeatherDBRepo)

        weatherDatabaseViewModel= WeatherDatabaseViewModel(insertWeatherDBUseCase,deleteWeatheDBUseCase,getWeatherUseCase)
    }
    @After
    fun close() {
        //   close Database
    }

    @Test
    fun getAllWeatherFromRoom_ZeroInput_checkSizeIsOne() = runBlocking<Unit>{
        // Given

        // When

        weatherDatabaseViewModel.getAllWeatherFromRoom()
         weatherDatabaseViewModel.allWeatherFromRoom.first()

        // Then
    }

    @Test
    fun insertWeatherInRoom() = runBlocking{
        // Given
        // When
        weatherDatabaseViewModel.insertWeatherInRoom(weatherDB.toDataModel())

        //Thin
    }


    @Test
    fun deleteWeatherLocation_insertLocationInRoom_CheckLisEmptyt()  = runBlocking{
        // Given
        weatherDatabaseViewModel.insertWeatherInRoom(weatherDB.toDataModel())

        // When
        weatherDatabaseViewModel.deleteWeatherFromRoom(weatherDB.toDataModel())

        //Thin
        weatherDatabaseViewModel.getAllWeatherFromRoom()
        var result = weatherDatabaseViewModel.allWeatherFromRoom.first()
        assertThat(result, IsEmptyCollection.empty())

    }


}

class FakeWeatherDBRepo : WeatherDBRepo {


    override suspend fun getAll(): List<WeatherDB> {
        return emptyList()
    }

    override suspend fun insert(weatherDB: WeatherDB) {

    }

    override suspend fun delete(weatherDB: WeatherDB) {
    }

}
