package com.example.domain.usecase

import com.example.domain.entity.ModelDatabase.Favourite
import com.example.domain.repo.FavRepo

class DeleteFavUseCase(private val favRepo: FavRepo) {
    suspend operator fun invoke(favouriteModel: Favourite)=favRepo.delete(favouriteModel)
}