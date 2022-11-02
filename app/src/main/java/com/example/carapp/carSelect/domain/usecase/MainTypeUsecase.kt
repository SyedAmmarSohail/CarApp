package com.example.carapp.carSelect.domain.usecase

import com.example.carapp.carSelect.domain.model.Type
import com.example.carapp.carSelect.domain.repository.CarRepository

class MainTypeUsecase(
    private val repository: CarRepository
) {

    suspend operator fun invoke(manufacturerId: String): Result<MutableMap<String, Type>> {
        return repository.getMainTypes(manufacturerId)
    }
}