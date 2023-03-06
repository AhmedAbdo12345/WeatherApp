package com.example.domain.usecase

import com.example.domain.entity.ModelDatabase.Favourite
import com.example.domain.repo.FavRepo


class InsertFavUseCase(private val favRepo: FavRepo) {
    suspend operator fun invoke(favourite: Favourite)=favRepo.insert(favourite)
}