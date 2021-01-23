package com.example.core.remote

import com.example.core.data.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndPointInterface {

    @GET("place/nearbysearch/json?")
    suspend fun apiGetPlaceDetail(
        @Query(value = "location", encoded = true) location: String,
        @Query(value = "rankby", encoded = true) rankby: String,
        @Query(value = "key", encoded = true) key: String
    ): PlaceData.Result
}