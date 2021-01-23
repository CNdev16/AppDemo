package com.example.core.remote

import android.util.Log
import com.example.core.DataSource
import com.example.core.UseCaseResult
import com.example.core.data.PlaceData
import com.example.core.handleErrorMessage
import com.example.core.handleThrowable
import com.google.gson.Gson

class RemoteDataSource(private val mApi: ApiEndPointInterface) : DataSource {
    override suspend fun getPlaceDataService(
        location: String,
        rankby: String,
        key: String
    ): PlaceData.Result {
        return mApi.apiGetPlaceDetail(location, rankby, key)
    }
}