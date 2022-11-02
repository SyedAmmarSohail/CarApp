package com.example.carapp.carSelect.data.di

import com.example.carapp.carSelect.data.intercepter.QueryInterceptor
import com.example.carapp.carSelect.data.remote.CarApi
import com.example.carapp.carSelect.data.repository.CarRepositoryImp
import com.example.carapp.carSelect.domain.repository.CarRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CarDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        queryInterceptor: QueryInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(
                queryInterceptor
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideCarApi(client: OkHttpClient, moshi: Moshi): CarApi {
        return Retrofit.Builder()
            .baseUrl(CarApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideCarRepository(
        api: CarApi,
        moshi: Moshi
    ): CarRepository {
        return CarRepositoryImp(
            api = api,
            moshi = moshi
        )
    }
}