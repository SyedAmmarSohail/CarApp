package com.example.carapp.carSelect.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CarResponse(
    @Json(name="data")
    var data: Map<String, String>
)
