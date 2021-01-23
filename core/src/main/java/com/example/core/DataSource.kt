package com.example.core

import com.example.core.data.*

interface DataSource {
    /*
    *   service.
     */
    suspend fun getPlaceDataService(
        location: String,
        rankby: String,
        key: String
    ): PlaceData.Result
}