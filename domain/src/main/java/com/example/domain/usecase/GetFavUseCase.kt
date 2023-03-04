package com.example.domain.usecase

import com.example.domain.repo.FavRepo

class GetFavUseCase(private val favRepo: FavRepo) {
    suspend operator fun invoke()=favRepo.getAll()
}