package com.example.carapp.carSelect.domain.usecase

import com.example.carapp.carSelect.domain.model.Type
import com.example.carapp.carSelect.domain.repository.CarRepository

class ManufacturerUsecase(
    private val repository: CarRepository
) {

    suspend operator fun invoke(page: Int, pageSize: Int): Result<MutableMap<String, Type>> {
        return repository.getManufacturer(page, pageSize)
    }
}