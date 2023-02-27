package com.example.domain.usecase

import com.example.domain.entity.ModelDatabase.FavouriteModel
import com.example.domain.repo.FavRepo

class DeleteFavUseCase(private val favRepo: FavRepo) {
    suspend operator fun invoke(favouriteModel: FavouriteModel)=favRepo.delete(favouriteModel)
}