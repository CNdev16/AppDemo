package com.example.appdemo.di

import com.example.core.BASE_URL
import com.example.core.DataRepository
import com.example.core.DataSource
import com.example.core.remote.ApiEndPointInterface
import com.example.core.remote.ApiService
import com.example.core.remote.RemoteDataSource
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

val appModule = module {
    single { ApiService(BASE_URL) }
    single<DataSource>(StringQualifier("remote")) {
        val apiService: ApiService = get()
        RemoteDataSource(
            apiService.getEndpointInterface(
                ApiEndPointInterface::class.java
            )
        )
    }
    single { DataRepository(get(StringQualifier("remote"))) }
}
