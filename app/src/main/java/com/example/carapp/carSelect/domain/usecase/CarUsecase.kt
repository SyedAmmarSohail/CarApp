package com.example.carapp.carSelect.domain.usecase

data class CarUsecase(
    val manufacturerUsecase: ManufacturerUsecase,
    val mainTypeUsecase: MainTypeUsecase,
    val builtDateUsecase: BuiltDateUsecase
)
