package com.example.core

import com.example.core.data.*

class DataRepository(
    private var remoteDataSource: DataSource
) {
    suspend fun getPlaceDataService(
        location: String,
        rankby: String,
        key: String
    ): PlaceData.Result {
        return remoteDataSource.getPlaceDataService(location, rankby, key)
    }
}