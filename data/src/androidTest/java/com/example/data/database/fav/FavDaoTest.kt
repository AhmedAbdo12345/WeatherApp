package com.example.data.database.fav

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
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
class FavDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var db: FavDatabase
    lateinit var dao: FavDao

    @Before
    fun initDB() {
        //   initialization Database
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FavDatabase::class.java
        )
            .allowMainThreadQueries().build()
        dao = db.getFavDao()
    }

    @After
    fun closeDB() {
        //   close Database
        db.close()
    }

    @Test
    fun getFavourite_insertItems_countItems() = runBlocking {
        // Given
        var data = FavouriteModel("Suez", 32.052, 29.08)
        dao.insert(data)
        var data2 = FavouriteModel("Cairo", 75.055, 15.08)
        dao.insert(data2)

        // When
        var result = dao.getAll()

        // Then
        MatcherAssert.assertThat(result.size, Is.`is`(2))
    }

    @Test
    fun insertFav_insertItem_returnItem() = runBlocking{
        // Given
        var data = FavouriteModel("Sohag", 85.052, 20.08)


        // When
        dao.insert(data)
        // Then
        var result = dao.getAll()
        MatcherAssert.assertThat(result[0], IsNull.notNullValue())
    }

    @Test
    fun deleteFav_deleteItem_checkIsNull() = runBlocking {
        // Given
        var data = FavouriteModel("Sohag", 85.052, 20.08)
        dao.insert(data)
        // When
        dao.delete(data)
        // Then
        var result = dao.getAll()
        assertThat(result, IsEmptyCollection.empty())
        assertThat(result.size,`is`(0))
    }
}