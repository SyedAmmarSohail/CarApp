package com.example.carapp.carSelect.data.mapper

import com.example.carapp.carSelect.data.remote.dto.CarResponse
import com.example.carapp.carSelect.domain.model.Type

fun CarResponse.toCarModel(): MutableMap<String, Type> {
    val manufacturerMap = hashMapOf<String, Type>()
    this.data.forEach { entry ->
        manufacturerMap[entry.key] = Type(entry.value)
    }
    return manufacturerMap
}