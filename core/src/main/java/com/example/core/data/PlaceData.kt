package com.example.core.data

import com.google.gson.annotations.SerializedName


class PlaceData {
    inner class Result {
        @SerializedName("results")
        var placeDetail: List<PlaceDetail> = ArrayList()

        @SerializedName("status")
        var status: String? = null
    }

    inner class PlaceDetail {
        @SerializedName("geometry")
        var geometry: Geometry? = null

        @SerializedName("vicinity")
        var vicinity: String? = null

        @SerializedName("name")
        var name: String? = null

        @SerializedName("icon")
        var icon: String? = null
    }

    inner class Geometry {
        @SerializedName("location")
        var locationDetail: LocationDetail? = null
    }

    inner class LocationDetail {
        @SerializedName("lat")
        var lat: String? = null

        @SerializedName("lng")
        var lng: String? = null
    }
}