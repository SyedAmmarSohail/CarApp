package com.example.carapp.carSelect.domain.usecase

import com.example.carapp.carSelect.domain.model.Type
import com.example.carapp.carSelect.domain.repository.CarRepository

class BuiltDateUsecase(
    private val repository: CarRepository
) {

    suspend operator fun invoke(
        manufacturerId: String,
        mainTypeId: String
    ): Result<MutableMap<String, Type>> {
        return repository.getBuiltDates(manufacturerId, mainTypeId)
    }
}