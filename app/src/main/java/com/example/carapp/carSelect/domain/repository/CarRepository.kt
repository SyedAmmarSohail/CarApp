package com.example.carapp.carSelect.domain.repository

import com.example.carapp.carSelect.domain.model.Type

interface CarRepository {

    suspend fun getManufacturer(page: Int, pageSize: Int): Result<MutableMap<String, Type>>
    suspend fun getMainTypes(manufacturerId: String): Result<MutableMap<String, Type>>
    suspend fun getBuiltDates(
        manufacturerId: String,
        mainTypeId: String
    ): Result<MutableMap<String, Type>>
}