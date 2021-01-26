package com.example.appdemo.ui.place

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdemo.R
import com.example.appdemo.databinding.ActivityPlaceListBinding
import com.example.appdemo.ui.place.adapter.PlaceAdapter
import com.example.appdemo.ui.place.paging.PlaceLoadStateAdapter
import com.example.appdemo.ui.place.viewmodel.PlaceListViewModel
import com.example.core.MY_LOCATION
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaceListActivity : AppCompatActivity() {

    private lateinit var _googleApiClient: GoogleApiClient
    private lateinit var _binding: ActivityPlaceListBinding
    private lateinit var _adapter: PlaceAdapter

    private val _viewModel: PlaceListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_place_list)

        _googleApiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(connectionCallbackListener())
            .addOnConnectionFailedListener(connectionFailedListener())
            .build()

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

        /*
        lifecycleScope.launch {
            _viewModel.placeData.collectLatest {
                _adapter.submitData(it)
            }
        }
         */
    }

    private fun connectionCallbackListener() = object : GoogleApiClient.ConnectionCallbacks {
        override fun onConnected(p0: Bundle?) {
            if (ActivityCompat.checkSelfPermission(
                    this@PlaceListActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this@PlaceListActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            val locationAvailable =
                LocationServices.FusedLocationApi.getLocationAvailability(_googleApiClient)
            if (locationAvailable.isLocationAvailable) {
                val locationRequest = LocationRequest.create().apply {
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    interval = 5000
                    fastestInterval = 1000
                }
                LocationServices.FusedLocationApi.requestLocationUpdates(
                    _googleApiClient,
                    locationRequest,
                    locationListener()
                )

                val fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(this@PlaceListActivity)
                fusedLocationClient.lastLocation
                    .addOnSuccessListener {
                        MY_LOCATION = Pair(
                            it?.latitude?.toString() ?: "0.00",
                            it?.longitude?.toString() ?: "0.00"
                        )
                        submitData()
                    }
            } else {

            }
        }

        override fun onConnectionSuspended(p0: Int) {
        }

    }

    private fun submitData() {
        lifecycleScope.launch {
            _viewModel.placeData.collectLatest { value ->
                _adapter.submitData(value)
            }
        }
    }

    private fun connectionFailedListener() = GoogleApiClient.OnConnectionFailedListener { }

    private fun locationListener() = LocationListener {
        MY_LOCATION = Pair(it?.latitude?.toString() ?: "0.00", it?.longitude?.toString() ?: "0.00")
        submitData()
    }

    override fun onStart() {
        super.onStart()
        _googleApiClient.connect()
    }

    override fun onStop() {
        super.onStop()
        if (_googleApiClient.isConnected) {
            _googleApiClient.disconnect()
        }
    }
}