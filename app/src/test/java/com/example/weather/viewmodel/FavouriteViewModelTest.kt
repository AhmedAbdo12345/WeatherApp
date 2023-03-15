package com.example.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.database.fav.FavouriteModel
import com.example.domain.entity.ModelDatabase.Favourite
import com.example.domain.repo.FavRepo
import com.example.domain.usecase.FavUseCase.DeleteFavUseCase
import com.example.domain.usecase.FavUseCase.GetFavUseCase
import com.example.domain.usecase.FavUseCase.InsertFavUseCase
import com.example.domain.usecase.WeatherDBUseCase.DeleteWeatheDBUseCase
import com.example.domain.usecase.WeatherDBUseCase.GetWeatherDBUseCase
import com.example.domain.usecase.WeatherDBUseCase.InsertWeatherDBUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FavouriteViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    lateinit var favouriteViewModel: FavouriteViewModel
    var favouriteModel=FavouriteModel("Suez",15.0525,52.55)
    @Before
    fun init()  {
        //   initialization Database
var fakeFavRepo=FakeFavRepo()

   val getFavUseCase=GetFavUseCase(fakeFavRepo)
   val insertFavUseCase=InsertFavUseCase(fakeFavRepo)
   val deleteFavUseCase= DeleteFavUseCase(fakeFavRepo)

        favouriteViewModel= FavouriteViewModel(insertFavUseCase,deleteFavUseCase,getFavUseCase)
    }
    @Test
    fun insertFavourite() {
        // Given

        // When
        favouriteViewModel.insertFavourite(favouriteModel)

        // Then
    }


    @Test
    fun deleteFavourite() {
        // Given

        // When
        favouriteViewModel.deleteFavourite(favouriteModel)
        // Then
    }

    @Test
    fun getAllFavourite() {
        // Given

        // When
        favouriteViewModel.getAllFavourite()

        // Then
    }
}
class FakeFavRepo : FavRepo{
    override suspend fun getAll(): List<Favourite> {
        return emptyList()
    }

    override suspend fun insert(favourite: Favourite) {
    }

    override suspend fun delete(favourite: Favourite) {
    }

}