package com.example.carapp.carSelect.data.repository

import com.example.carapp.carSelect.data.remote.CarApi
import com.example.carapp.carSelect.data.remote.dto.CarResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

internal class CarRepositoryImpTest{

    private lateinit var repository: CarRepositoryImp

    @MockK
    private lateinit var carApi: CarApi

    @Before
    fun setup(){
        MockKAnnotations.init(this, relaxed = true)
        repository = CarRepositoryImp(
            api = carApi
        )
    }

    /* region Manufacturer Service Test*/

    @Test
    fun `get manufacturer, return success`(): Unit = runBlocking {
        coEvery { carApi.getManufacturer(PAGE, PAGE_SIZE) } returns Response.success(getCarDto())

        val result = repository.getManufacturer(PAGE, PAGE_SIZE)

        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `get manufacturer, return error`(): Unit = runBlocking {
        coEvery { carApi.getManufacturer(PAGE, PAGE_SIZE) } returns Response.error(400, responseBody())

        val result = repository.getManufacturer(PAGE, PAGE_SIZE)

        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `get manufacturer, return exception`(): Unit = runBlocking {
        coEvery { carApi.getManufacturer(PAGE, PAGE_SIZE) } throws IOException()

        val result = repository.getManufacturer(PAGE, PAGE_SIZE)

        assertThat(result.isFailure).isTrue()
    }

    /*endregion*/

    /* region mainType Service Test*/

    @Test
    fun `get main type, return success`(): Unit = runBlocking {
        coEvery { carApi.getMainType(MANUFACTURER_ID) } returns Response.success(getCarDto())

        val result = repository.getMainTypes(MANUFACTURER_ID)

        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `get main type, return error`(): Unit = runBlocking {
        coEvery { carApi.getMainType(MANUFACTURER_ID) } returns Response.error(400, responseBody())

        val result = repository.getMainTypes(MANUFACTURER_ID)

        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `get main type, return exception`(): Unit = runBlocking {
        coEvery { carApi.getMainType(MANUFACTURER_ID) } throws IOException()

        val result = repository.getMainTypes(MANUFACTURER_ID)

        assertThat(result.isFailure).isTrue()
    }

    /*endregion*/

    /* region mainType Service Test*/

    @Test
    fun `get built date, return success`(): Unit = runBlocking {
        coEvery { carApi.getBuiltDates(MANUFACTURER_ID, MAIN_TYPE_ID) } returns Response.success(getCarDto())

        val result = repository.getBuiltDates(MANUFACTURER_ID, MAIN_TYPE_ID)

        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `get built date, return error`(): Unit = runBlocking {
        coEvery { carApi.getBuiltDates(MANUFACTURER_ID, MAIN_TYPE_ID) } returns Response.error(400, responseBody())

        val result = repository.getBuiltDates(MANUFACTURER_ID, MAIN_TYPE_ID)

        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `get built date, return exception`(): Unit = runBlocking {
        coEvery { carApi.getBuiltDates(MANUFACTURER_ID, MAIN_TYPE_ID) } throws IOException()

        val result = repository.getBuiltDates(MANUFACTURER_ID, MAIN_TYPE_ID)

        assertThat(result.isFailure).isTrue()
    }

    /*endregion*/

    private fun responseBody() = ResponseBody.create("application/json".toMediaTypeOrNull(), "")

    private fun getCarDto() = CarResponse(
        data = mutableMapOf(
            "key1" to "value1",
            "key2" to "value2",
            "key3" to "value3",
        )
    )
    companion object{
        const val PAGE = 1
        const val PAGE_SIZE = 15
        const val MANUFACTURER_ID = "manufacturer-id"
        const val MAIN_TYPE_ID = "main-type-id"
    }
}