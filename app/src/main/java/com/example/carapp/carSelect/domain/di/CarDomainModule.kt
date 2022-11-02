package com.example.carapp.carSelect.domain.di

import com.example.carapp.carSelect.domain.repository.CarRepository
import com.example.carapp.carSelect.domain.usecase.BuiltDateUsecase
import com.example.carapp.carSelect.domain.usecase.CarUsecase
import com.example.carapp.carSelect.domain.usecase.MainTypeUsecase
import com.example.carapp.carSelect.domain.usecase.ManufacturerUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object CarDomainModule {

    @ViewModelScoped
    @Provides
    fun provideCarUseCases(
        repository: CarRepository,
    ): CarUsecase {
        return CarUsecase(
            manufacturerUsecase = ManufacturerUsecase(repository),
            mainTypeUsecase = MainTypeUsecase(repository),
            builtDateUsecase = BuiltDateUsecase(repository)
        )
    }
}