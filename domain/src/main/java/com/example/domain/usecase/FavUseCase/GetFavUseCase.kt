package com.example.domain.usecase.FavUseCase

import com.example.domain.repo.FavRepo

class GetFavUseCase(private val favRepo: FavRepo) {
    suspend operator fun invoke()=favRepo.getAll()
}