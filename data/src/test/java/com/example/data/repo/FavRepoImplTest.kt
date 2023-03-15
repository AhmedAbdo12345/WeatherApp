package com.example.data.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.database.fav.FavDatabase
import com.example.data.database.weather.WeatherDatabase
import com.example.data.mapper.toDomainModel
import com.example.domain.entity.ModelDatabase.Favourite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
class FavRepoImplTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var db: FavDatabase
    lateinit var favRepoImpl:FavRepoImpl

    @Before
    fun initDB() {
        //   initialization Database
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FavDatabase::class.java
        ).allowMainThreadQueries().build()

        favRepoImpl = FavRepoImpl(db)
    }

    @After
    fun closeDB() {
        //   close Database
        db.close()
    }

    @Test
    fun getFavourite_insertInFavourite_countPlaces() = runBlocking<Unit> {
        // Given
        val favourite=Favourite("Suez",32.025,29.56)
        val favourite2=Favourite("Cairo",50.025,10.56)
        val favourite3=Favourite("Alex",60.025,70.56)
        favRepoImpl.insert(favourite)
        favRepoImpl.insert(favourite2)
        favRepoImpl.insert(favourite3)
        // When
        val result = favRepoImpl.getAll()

        // Then
       MatcherAssert.assertThat(result.size, Is.`is`(3))

    }

    @Test
    fun insertFavourite_insertInRoom_countPlaces() = runBlocking{
        // Given
        val favourite=Favourite("Suez",32.025,29.56)
        val favourite2=Favourite("Cairo",50.025,10.56)
        val favourite3=Favourite("Alex",60.025,70.56)

        // When
        favRepoImpl.insert(favourite)
        favRepoImpl.insert(favourite2)
        favRepoImpl.insert(favourite3)

        // Then
        val result = favRepoImpl.getAll()
        MatcherAssert.assertThat(result.size, Is.`is`(3))
        MatcherAssert.assertThat(result[0], IsNull.notNullValue())
        MatcherAssert.assertThat(result[1], IsNull.notNullValue())
        MatcherAssert.assertThat(result[2], IsNull.notNullValue())
    }

    @Test
    fun deleteFavPlace_insertInRoom_CheckListIsEmpty() = runBlocking{
        // Given
        val favourite=Favourite("Suez",32.025,29.56)
        val favourite2=Favourite("Cairo",50.025,10.56)
        val favourite3=Favourite("Alex",60.025,70.56)
        favRepoImpl.insert(favourite)
        favRepoImpl.insert(favourite2)
        favRepoImpl.insert(favourite3)
        // When
        favRepoImpl.delete(favourite)
        favRepoImpl.delete(favourite2)
        favRepoImpl.delete(favourite3)

        // Then
        val result = favRepoImpl.getAll()
        MatcherAssert.assertThat(result.size, Is.`is`(0))
        assertThat(result, IsEmptyCollection.empty())
    }
}