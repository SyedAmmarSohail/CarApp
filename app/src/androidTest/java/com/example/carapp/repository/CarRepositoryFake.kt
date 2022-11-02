package com.example.carapp.repository

import com.example.carapp.carSelect.domain.model.Type
import com.example.carapp.carSelect.domain.repository.CarRepository

class CarRepositoryFake : CarRepository {

    var shouldReturnError = false

    var resultList = mutableMapOf(
        "key1" to Type(title = "title1"),
        "key2" to Type(title = "title2"),
        "key3" to Type(title = "title3"),
    )

    override suspend fun getManufacturer(
        page: Int,
        pageSize: Int
    ): Result<MutableMap<String, Type>> {
        return if (shouldReturnError) {
            Result.failure(Throwable())
        } else {
            Result.success(resultList)
        }
    }

    override suspend fun getMainTypes(manufacturerId: String): Result<MutableMap<String, Type>> {
        return if (shouldReturnError) {
            Result.failure(Throwable())
        } else {
            Result.success(resultList)
        }
    }

    override suspend fun getBuiltDates(
        manufacturerId: String,
        mainTypeId: String
    ): Result<MutableMap<String, Type>> {
        return if (shouldReturnError) {
            Result.failure(Throwable())
        } else {
            Result.success(resultList)
        }
    }
}