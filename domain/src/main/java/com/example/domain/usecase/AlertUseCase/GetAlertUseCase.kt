package com.example.domain.usecase.AlertUseCase

import com.example.domain.entity.ModelDatabase.Alert
import com.example.domain.repo.AlertRepo

class GetAlertUseCase (val alertRepo: AlertRepo) {
    suspend operator fun invoke()=alertRepo.getAll()

}