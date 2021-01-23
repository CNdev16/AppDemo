package com.example.appdemo.ui.place.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.appdemo.ui.place.paging.PlacePagingSource
import com.example.core.DataRepository
import com.example.core.data.PlaceData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class PlaceListViewModel(private val dataRepository: DataRepository) : ViewModel(), CoroutineScope {

    companion object {
        private const val PAGE_SIZE = 20
    }

    private val _job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + _job

    val placeData: Flow<PagingData<PlaceData.PlaceDetail>> = Pager(PagingConfig(PAGE_SIZE)) {
        PlacePagingSource(dataRepository = dataRepository)
    }.flow.cachedIn(viewModelScope)

    override fun onCleared() {
        _job.cancel()
        super.onCleared()
    }
}