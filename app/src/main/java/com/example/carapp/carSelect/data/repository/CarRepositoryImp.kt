package com.example.carapp.carSelect.data.repository

import com.example.carapp.CarApp
import com.example.carapp.carSelect.data.mapper.toCarModel
import com.example.carapp.carSelect.data.remote.CarApi
import com.example.carapp.carSelect.data.remote.dto.CarResponse
import com.example.carapp.carSelect.domain.repository.CarRepository
import com.example.carapp.carSelect.domain.model.Type
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.delay

class CarRepositoryImp(
    private val api: CarApi,
    private val moshi: Moshi
) : CarRepository {


    override suspend fun getManufacturer(
        page: Int,
        pageSize: Int
    ): Result<MutableMap<String, Type>> {
        return try {
            delay(500)
            val carResponse = readJsonFile("car_manufacturer.json")
            carResponse?.let {
                val startingIndex = page * pageSize
                if (startingIndex + pageSize <= it.data.size) {
                    it.data =
                        it.data.toList().slice(startingIndex until startingIndex + pageSize).toMap()
                    Result.success(
                        it.toCarModel()
                    )
                } else Result.success(
                    CarResponse(data = emptyMap()).toCarModel()
                )
            } ?: Result.failure(Exception("Service Failed"))

//            TODO(uncomment below code if you have actual api)
/*             val response = api.getManufacturer(page, pageSize)
             if (response.isSuccessful) {
                 val carManufacturerResponse = response.body() as CarResponse
                 Result.success(
                     carManufacturerResponse.toCarModel()
                 )
             } else {
                 Result.failure(Exception("Service Failed"))
             }*/
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    private fun readJsonFile(jsonFile: String): CarResponse? {
        val listType = Types.newParameterizedType(
            CarResponse::class.java, Types.newParameterizedType(
                MutableMap::class.java,
                String::class.java,
            )
        )
        val adapter: JsonAdapter<CarResponse> = moshi.adapter(listType)
        val carJson =
            CarApp.appContext.assets.open(jsonFile).bufferedReader()
                .use { it.readText() }
        return adapter.fromJson(carJson)
    }

    override suspend fun getMainTypes(manufacturerId: String): Result<MutableMap<String, Type>> {
        return try {
            val carResponse = readJsonFile("car_model.json")
            carResponse?.let {
                Result.success(
                    it.toCarModel()
                )
            } ?: Result.failure(Exception("Service Failed"))

//            TODO(uncomment below code if you have actual api)
/*            val response = api.getMainType(manufacturerId)
            if (response.isSuccessful) {
                val carManufacturerResponse = response.body() as CarResponse
                Result.success(
                    carManufacturerResponse.toCarModel()
                )
            } else {
                Result.failure(Exception("Service Failed"))
            }*/
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getBuiltDates(
        manufacturerId: String,
        mainTypeId: String
    ): Result<MutableMap<String, Type>> {
        return try {
            val carResponse = readJsonFile("car_year.json")
            carResponse?.let {
                Result.success(
                    it.toCarModel()
                )
            } ?: Result.failure(Exception("Service Failed"))

//            TODO(uncomment below code if you have actual api)
/*            val response = api.getBuiltDates(manufacturerId, mainTypeId)
            if (response.isSuccessful) {
                val carManufacturerResponse = response.body() as CarResponse
                Result.success(
                    carManufacturerResponse.toCarModel()
                )
            } else {
                Result.failure(Exception("Service Failed"))
            }*/
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}