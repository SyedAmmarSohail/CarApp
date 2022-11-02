package com.example.carapp.carSelect.data.remote

import com.example.carapp.carSelect.data.remote.dto.CarResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CarApi {

    @GET("car-manufacturer")
    suspend fun getManufacturer(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): Response<CarResponse>

    @GET("car-model")
    suspend fun getMainType(
        @Query("manufacturer") manufacturer: String
    ): Response<CarResponse>

    @GET("car-year")
    suspend fun getBuiltDates(
        @Query("manufacturer") manufacturer: String,
        @Query("car-model") mainType: String,
    ): Response<CarResponse>

    companion object {
        const val BASE_URL = "https://BASE_URL/"
        const val API_KEY = "API_KEY"
    }
}