package com.example.appdemo.ui.place

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdemo.R
import com.example.appdemo.databinding.ActivityPlaceListBinding
import com.example.appdemo.ui.place.adapter.PlaceAdapter
import com.example.appdemo.ui.place.paging.PlaceLoadStateAdapter
import com.example.appdemo.ui.place.viewmodel.PlaceListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaceListActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityPlaceListBinding
    private lateinit var _adapter: PlaceAdapter

    private val _viewModel: PlaceListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_place_list)
        setupUI()
    }

    private fun setupUI() {
        _adapter = PlaceAdapter()

        _binding.rcView.apply {
            layoutManager = LinearLayoutManager(this@PlaceListActivity)
            setHasFixedSize(true)
            adapter = _adapter.withLoadStateFooter(
                footer = PlaceLoadStateAdapter()
            )
        }

        _adapter.addLoadStateListener { loadState ->
            /**
            This code is taken from https://medium.com/@yash786agg/jetpack-paging-3-0-android-bae37a56b92d
             **/
            if (loadState.refresh is LoadState.Loading) {
                _binding.progresbar.visibility = View.VISIBLE
            } else {
                _binding.progresbar.visibility = View.GONE

                // getting the error
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    Toast.makeText(this@PlaceListActivity, it.error.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        lifecycleScope.launch {
            _viewModel.placeData.collectLatest {
                _adapter.submitData(it)
            }
        }
    }
}