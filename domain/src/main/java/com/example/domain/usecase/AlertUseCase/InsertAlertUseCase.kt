package com.example.domain.usecase.AlertUseCase

import com.example.domain.entity.ModelDatabase.Alert
import com.example.domain.repo.AlertRepo

class InsertAlertUseCase (val alertRepo: AlertRepo) {
    suspend operator fun invoke(alert: Alert)=alertRepo.insert(alert)

}