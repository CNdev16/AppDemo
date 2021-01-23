package com.example.appdemo.ui.place.paging

import androidx.paging.PagingSource
import com.example.core.DataRepository
import com.example.core.GOOGLE_API_KEY
import com.example.core.MY_LOCATION
import com.example.core.data.PlaceData
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class PlacePagingSource(private val dataRepository: DataRepository) :
    PagingSource<Int, PlaceData.PlaceDetail>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlaceData.PlaceDetail> {
        val position = params.key ?: 1
        return try {
            val response = dataRepository.getPlaceDataService(
                "${MY_LOCATION.first},${MY_LOCATION.second}",
                "distance",
                GOOGLE_API_KEY
            )
            try {
                if (response.status == "OK") {
                    val placeData = response.placeDetail
                    val nextKey = if (placeData.isEmpty()) {
                        null
                    } else {
                        position + 1
                    }
                    LoadResult.Page(
                        data = placeData,
                        prevKey = if (position == 1) null else position - 1,
                        nextKey = nextKey
                    )
                } else {
                    return LoadResult.Error(
                        throw IllegalArgumentException("Status: ${response.status}, please check your request information and try again.\nResult: ${response.placeDetail}")
                    )
                }
            } catch (exception: Exception) {
                return LoadResult.Error(exception)
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}